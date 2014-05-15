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

import javax.swing.SwingWorker;

import model.Borrower;
import model.BorrowerCollection;
import model.Rental;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;
import document.DocumentFactory;
import document.ExcelDocument;

/**
 * Transaction that handles listing all borrowers that currently have books rented
 */
public class ListBorrowersWithRentedBooksTransaction extends Transaction {

	/** Borrower that rented the books that is being examined */
	private Borrower borrower;
	
	/** list all borrowers with rented books */
	private List<Borrower> borrowers;
	
	/** Rentals for this borrower */
	private List<Rental> rentals;

	/**
	 * Constructs List Borrowers With Rented Books Transaction
	 * @param parentController
	 */
	public ListBorrowersWithRentedBooksTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute() {
		showView("ListBorrowersWithRentedBooksView");
	}

	public void exportToExcel(){
		ExcelDocument document = DocumentFactory.createExcelDocument("ListBorrowersWithRentedBooksDocument", this);
		if(document.save()){
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The file was successfully saved."));
		}else{
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.WARNING, "Heads up! The file was not saved."));
		}
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
	 * Retrieves Borrowers and Updates list
	 */
	private void getBorrowers() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {

				BorrowerCollection borrowerCollection = new BorrowerCollection();
				borrowerCollection.findWithOutstandingRentals();
				borrowers = borrowerCollection.getEntities();
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.BORROWER_COLLECTION, null);
			}
		}.execute();
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.BORROWER_COLLECTION)) {
			return borrowers;
		}else if(key.equals(Key.RENTAL_COLLECTION)){
			return rentals;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if (key.equals(Key.REFRESH_LIST)) {
			getBorrowers();
		}else if(key.equals(Key.SELECT_BORROWER)){
			selectBorrower((Borrower)value);
		}else if (key.equals(Key.EXPORT_TO_EXCEL)) {
			exportToExcel();
		}else if(key.equals(Key.BACK)){
			showView("ListBorrowersWithRentedBooksView");
		}
		super.stateChangeRequest(key, value);
	}
}
