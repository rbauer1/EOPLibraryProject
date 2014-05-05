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
import utilities.Key;
import controller.Controller;
import document.DocumentFactory;
import document.ExcelDocument;

/**
 * Transaction that handles listing all borrowers that currently have books rented
 */
public class ListBorrowersWithRentedBooksTransaction extends Transaction {

	/** list all borrowers with rented books */
	private List<Borrower> borrowers;

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
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if (key.equals(Key.REFRESH_LIST)) {
			getBorrowers();
		}else if (key.equals(Key.EXPORT_TO_EXCEL)) {
			ExcelDocument document = DocumentFactory.createExcelDocument("ListBorrowersWithRentedBooksDocument", this);
			document.save();
		}
		super.stateChangeRequest(key, value);
	}
}
