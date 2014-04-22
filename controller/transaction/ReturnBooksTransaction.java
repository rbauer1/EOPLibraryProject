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

	/** The rentals being returned */
	private List<Rental> returnRentals;

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
	private void addRentalToReturns(Object value) {
		Rental rental;
		if(value instanceof String) {
			try {
				Book book = new Book((String)value);
				RentalCollection rentalCollection = new RentalCollection();
				rentalCollection.findOutstanding(book, borrower);
				List<Rental> rentals = rentalCollection.getEntities();
				if(rentals.size() == 0){
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw Shucks! The barcode you provided cannot be returned by this borrower."));
					return;
				}
				rental = rentals.get(0);
			} catch (InvalidPrimaryKeyException e) {
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw Shucks! The barcode you provided is invalid. Please try again."));
				return;
			}
		}else{
			rental = (Rental)value;
		}
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

	@Override
	public void execute(){
		worker = (Worker)parentController.getState(Key.WORKER);
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
		if(key.equals(Key.OUTSTANDING_RENTALS)){
			return outstandingRentals;
		}
		if(key.equals(Key.RETURN_RENTALS)){
			return returnRentals;
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
		JDBCBroker.getInstance().startTransaction();
		boolean saveSuccess = true;
		for(Rental rental : returnRentals){
			saveSuccess &= rental.checkIn(worker);
		}
		if(saveSuccess){
			JDBCBroker.getInstance().commitTransaction();
			//TODO print receipt
			stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
			parentController.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The books were succesfully returned."));
		}else{
			JDBCBroker.getInstance().rollbackTransaction();
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving the rentals."));
		}
	}

	/**
	 * Sets the borrower that is returning books
	 * @param borrower
	 */
	private void selectBorrower(Borrower borrower) {
		this.borrower = borrower;
		outstandingRentals = borrower.getOutstandingRentals().getEntities();
		returnRentals = new ArrayList<Rental>();
		showView("ReturnBooksView");
		stateChangeRequest(Key.REFRESH_LIST, null);
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
