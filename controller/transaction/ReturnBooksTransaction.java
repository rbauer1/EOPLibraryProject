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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Book;
import model.Borrower;
import model.Rental;
import model.RentalCollection;
import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;
import database.JDBCBroker;
import document.DocumentFactory;
import document.Receipt;
import exception.InvalidPrimaryKeyException;

/**
 * Transaction that handles the process of returning books
 */
public class ReturnBooksTransaction extends Transaction {

	/** The borrower returning books */
	private Borrower borrower;

	/** The worker performing the returns */
	private Worker worker;

	/** The rentals owed by the borrower */
	private List<Rental> outstandingRentals;
	private List<Book> outstandingBooks;

	/** The rentals being returned */
	private List<Rental> returnRentals;
	private List<Book> returnBooks;

	/** ListBorrower Transaction */
	private Transaction listBorrowersTransaction;

	/**
	 * Constructs Return Book Transaction
	 * @param parentController
	 */
	public ReturnBooksTransaction(Controller parentController) {
		super(parentController);
	}

	/**
	 * Adds the rental to the list to be returned
	 * @param value - rental or barcode
	 */
	private void addRentalToReturns(final Object value) {
		new SwingWorker<Rental, Void>() {
			@Override
			protected Rental doInBackground() {
				if(value instanceof String) {
					try {
						Book book = new Book((String)value);
						RentalCollection rentalCollection = new RentalCollection();
						rentalCollection.findOutstanding(book, borrower);
						List<Rental> rentals = rentalCollection.getEntities();
						if(rentals.size() == 0){
							stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw Shucks! The barcode you provided cannot be returned by this borrower."));
							return null;
						}
						return rentals.get(0);
					} catch (InvalidPrimaryKeyException e) {
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw Shucks! The barcode you provided is invalid. Please try again."));
						return null;
					}
				}
				return (Rental)value;
			}

			@Override
			public void done() {
				Rental rental;
				try {
					rental = get();
				} catch (InterruptedException e) {
					rental = null;
				} catch (ExecutionException e) {
					rental = null;
				}
				if(rental != null){
					outstandingRentals.remove(rental);
					if(returnRentals.contains(rental)) {
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.WARNING, "Heads up! The book was not added since it is already in the list of returns."));
						return;
					}
					if(rental.isLate()) {
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.WARNING, "Late! This book is past the due date! It was due on " + rental.getState("DueDate")));
					}
					returnRentals.add(rental);
					stateChangeRequest(Key.REFRESH_LIST, null);
				}
			}
		}.execute();
	}

	@Override
	public void execute(){
		worker = (Worker)parentController.getState(Key.LOGGED_IN_WORKER);
		listBorrowersTransaction = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
		listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower who is returning books from the list below."));
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.REFRESH_LIST, Key.OUTSTANDING_RENTALS + "," + Key.RETURN_RENTALS);
		return dependencies;
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		if(key.equals(Key.WORKER)){
			return worker;
		}
		if(key.equals(Key.OUTSTANDING_RENTALS)){
			return outstandingRentals;
		}
		if(key.equals(Key.RETURN_RENTALS)){
			return returnRentals;
		}
		if(key.equals(Key.OUTSTANDING_BOOKS)){
			return outstandingBooks;
		}
		if(key.equals(Key.BOOK_COLLECTION)){
			return returnBooks;
		}
		if(key.equals(Key.PRINT_DOCUMENT)){
			return "test.pdf";
		}
		return super.getState(key);
	}

	/**
	 * Removes rental from returns and places in outstanding
	 * @param rental
	 */
	private void removeRentalFromReturns(Rental rental) {
		returnRentals.remove(rental);
		outstandingRentals.add(rental);
		stateChangeRequest(Key.REFRESH_LIST, null);
	}

	private void returnBooks() {
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				JDBCBroker.getInstance().startTransaction();
				boolean saveSuccess = true;
				returnBooks = new ArrayList<Book>();
				outstandingBooks = new ArrayList<Book>();
				for(Rental rental : returnRentals){
					saveSuccess &= rental.checkIn(worker);
					returnBooks.add(rental.getBook());
				}
				for(Rental rental : outstandingRentals){
					outstandingBooks.add(rental.getBook());
				}
				return saveSuccess;
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
					Receipt reciept = DocumentFactory.createReceipt("ReturnBooksReceipt", ReturnBooksTransaction.this);
					reciept.save("test.pdf");
					System.out.println("shitt");
					TransactionFactory.executeTransaction(ReturnBooksTransaction.this, Key.EXECUTE_PRINT_PDF, Key.DISPLAY_MAIN_MENU);
					//stateChangeRequest(Key.DISPLAY_MAIN_MENU, null); //TODO necessary?
					//parentController.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The books were successfully returned."));
				}else{
					JDBCBroker.getInstance().rollbackTransaction();
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving the rentals."));
				}
			}
		}.execute();
	}

	/**
	 * Sets the borrower that is returning books
	 * @param borrower
	 */
	private void selectBorrower(final Borrower b) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				borrower = b;
				outstandingRentals = borrower.getOutstandingRentals().getEntities();
				returnRentals = new ArrayList<Rental>();
				return null;
			}

			@Override
			public void done() {
				showView("ReturnBooksView");
				stateChangeRequest(Key.REFRESH_LIST, null);
			}
		}.execute();
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			selectBorrower((Borrower)value);
		} else if(key.equals(Key.ADD_RENTAL_TO_LIST)){
			addRentalToReturns(value);
		} else if(key.equals(Key.REMOVE_RENTAL_FROM_LIST)){
			removeRentalFromReturns((Rental)value);
		} else if(key.equals(Key.RETURN_BOOKS)) {
			returnBooks();
		} else if(key.equals(Key.BACK)){
			String view = (String)value;
			if(view.equals("ListBorrowersView")){
				listBorrowersTransaction.execute();
				listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower who is returning books from the list below."));
			}else{
				showView(view);
			}
		} else if(key.equals(Key.DISPLAY_BORROWER_MENU)){ //TODO handle this back better
			key = Key.DISPLAY_MAIN_MENU;
		}
		super.stateChangeRequest(key, value);
	}
}
