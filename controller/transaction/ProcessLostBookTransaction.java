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

import model.Borrower;
import model.Rental;
import model.RentalCollection;
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
	
	/** List of errors in the input */
	private List<String> inputErrors;
	
	/**
	 * Constructs Process Lost Book Transaction
	 * @param parentController
	 */
	public ProcessLostBookTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BOOK, Key.BOOK);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.BOOK);
		return dependencies;
	}
	
	@Override
	public void execute(){
		TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BORROWER);
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
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			borrower = (Borrower)value;
			rentals = borrower.getRentals().getEntities();
			showView("ListRentalsView");
		}else if(key.equals(Key.SELECT_RENTAL)){
			selectedRental = (Rental)value;
		}
		registry.updateSubscribers(key, this);
	}
}	
