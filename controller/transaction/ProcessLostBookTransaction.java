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

import common.PDFGenerator;
import model.Book;
import model.Borrower;
import model.Rental;
import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles process a lost book.
 */
public class ProcessLostBookTransaction extends Transaction {

	/** Borrower Model this transaction is updating */
	private Borrower borrower;

	/** Rental Model that corresponds to lost book */
	private List<Rental> rentals;

	/** Selected Rental from table */
	private Rental selectedRental;

	/** Book Model this transaction is marking as lost */
	private Book book;

	/** List of errors in the input */
	private List<String> inputErrors;

	/** ListBorrower Transaction */
	private Transaction listBorrowers;

	/**
	 * Constructs Process Lost Book Transaction
	 * @param parentController
	 */
	public ProcessLostBookTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute(){
		listBorrowers = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_RENTAL, Key.BOOK + "," + Key.BORROWER);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.BOOK);
		return dependencies;
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		if(key.equals(Key.RENTAL_COLLECTION)){
			return rentals;
		}
		if(key.equals(Key.RENTAL)){
			return selectedRental;
		}
		if(key.equals(Key.BOOK)){
			return book;
		}
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
		}
		return super.getState(key);
	}

	private void processLostBook(Properties bookData){
		String notes = "Additional Notes: ";
		notes += bookData.getProperty("Notes", "");
		if(book.setLost(notes)){
			showView("ListRentalsView");
			borrower.addMonetaryPenaltyForLostBook(book);
			selectedRental.checkIn((Worker)parentController.getState(Key.WORKER));
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The book was marked as lost successfully, and this student's monetary penalty has been updated appropriately"));
			stateChangeRequest(Key.RENTAL_COLLECTION, null);
			PDFGenerator.generate(PDFGenerator.LOST_BOOK_ACTION,book, borrower, (Worker)parentController.getState(Key.WORKER));
		}else{
			List<String> inputErrors = book.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while marking as lost."));
			}
		}
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			borrower = (Borrower)value;
			stateChangeRequest(Key.RENTAL_COLLECTION, null);
			showView("ListRentalsView");
		} else if(key.equals(Key.SELECT_RENTAL)){
			selectedRental = (Rental)value;
			book = selectedRental.getBook();
			showView("LostBookView");
		} else if(key.equals(Key.SAVE_BOOK)){
			processLostBook((Properties)value);
		} else if(key.equals(Key.BACK)){
			String view = (String)value;
			if(view.equals("ListBorrowersView")){
				listBorrowers.execute();
			}else{
				showView(view);
			}
		} else if(key.equals(Key.RENTAL_COLLECTION)){
			rentals = borrower.getOutstandingRentals().getEntities();
		} else if(key.equals(Key.DISPLAY_BORROWER_MENU)){
			key = Key.DISPLAY_BOOK_MENU;
		} else if(key.equals(Key.EXECUTE_PRINT_PDF)){
			showView("PrintPDFView");
		}
		super.stateChangeRequest(key, value);
	}
}
