/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package controller.transaction;


import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Book;
import model.Borrower;
import model.Rental;
import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;
import database.JDBCBroker;
import document.DocumentFactory;
import document.Receipt;

/**
 * Transaction that handles process a lost book.
 */
public class ProcessLostBookTransaction extends Transaction {

	/** Borrower that rented the books that is being set as lost */
	private Borrower borrower;

	/** Rentals for this borrower */
	private List<Rental> rentals;

	/** Rental associated with lost book */
	private Rental rental;

	/** Book Model this transaction is marking as lost */
	private Book book;

	/** ListBorrower Transaction */
	private Transaction listBorrowersTransaction;

	/** The worker that is processing the lost book */
	private Worker worker;

	/**
	 * Constructs Process Lost Book Transaction
	 * @param parentController
	 */
	public ProcessLostBookTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute(){
		worker = (Worker)parentController.getState(Key.LOGGED_IN_WORKER);
		listBorrowersTransaction = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
		listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower who lost a book from the list below."));
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_RENTAL, Key.BOOK + "," + Key.BORROWER);
		return dependencies;
	}

	private void getRentals(){
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				rentals = borrower.getOutstandingRentals().getEntities();
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.RENTAL_COLLECTION, null);
			}
		}.execute();
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		if(key.equals(Key.WORKER)){
			return worker;
		}
		if(key.equals(Key.RENTAL_COLLECTION)){
			return rentals;
		}
		if(key.equals(Key.RENTAL)){
			return rental;
		}
		if(key.equals(Key.BOOK)){
			return book;
		}
		if(key.equals(Key.PRINT_DOCUMENT)){
			return "test.pdf";
		}
		return super.getState(key);
	}

	/**
	 * Marks Book as lost and saves it.
	 * @param bookData
	 */
	private void saveBook(final Properties bookData){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				JDBCBroker.getInstance().startTransaction();
				boolean success = true;
				success &= book.setLost(bookData);
				success &= borrower.setLostBook(book);
				success &= rental.checkIn(worker);
				return success;
			}

			@Override
			public void done() {
				boolean success = false;
				try {
					success = get();
				} catch (InterruptedException e) {
					success = false;
				} catch (ExecutionException e) {
					success = false;
				}
				if(success){
					JDBCBroker.getInstance().commitTransaction();
					//showView("ListRentalsView");
					//stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The book was marked as lost successfully, and the borrower was set as deliquent and their balance was updated."));
					getRentals();
					Receipt reciept = DocumentFactory.createReceipt("LostBookReceipt", ProcessLostBookTransaction.this);
					reciept.save("test.pdf");
					TransactionFactory.executeTransaction(ProcessLostBookTransaction.this, Key.EXECUTE_PRINT_PDF, Key.DISPLAY_MAIN_MENU);
				}else{
					JDBCBroker.getInstance().rollbackTransaction();
					List<String> inputErrors = book.getErrors();
					if(inputErrors.size() > 0){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
					}else{
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while marking as lost."));
					}
				}
			}
		}.execute();
	}

	/**
	 * Sets the borrower
	 * @param borrrower
	 */
	private void selectBorrower(Borrower borrrower){
		borrower = borrrower;
		showView("ListRentalsView");
		getRentals();
	}

	/**
	 * Sets the rental
	 * @param rental
	 */
	private void selectRental(Rental rental){
		this.rental = rental;
		book = rental.getBook();
		showView("LostBookView");
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			selectBorrower((Borrower)value);
		} else if(key.equals(Key.SELECT_RENTAL)){
			selectRental((Rental)value);
		} else if(key.equals(Key.SAVE_BOOK)){
			saveBook((Properties)value);
		} else if(key.equals(Key.BACK)){
			String view = (String)value;
			if(view.equals("ListBorrowersView")){
				listBorrowersTransaction.execute();
				listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower who lost a book from the list below."));
			}else{
				showView(view);
			}
		} else if(key.equals(Key.DISPLAY_BORROWER_MENU)){
			key = Key.DISPLAY_BOOK_MENU;
		}
		super.stateChangeRequest(key, value);
	}
}
