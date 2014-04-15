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
import model.BorrowerCollection;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles listing and selecting a borrower
 */
public class ListBorrowersTransaction extends Transaction {
	
	/** list of borrowers returned from search */
	private List<Borrower> borrowers;
	
	/** borrower selected from list */
	private Borrower selectedBorrower;
	
	/** type of operation, can be Delete or Modify */
	private String operationType;

	/**
	 * Constructs List Borrowers Transaction
	 * @param parentController
	 */
	public ListBorrowersTransaction(Controller parentController) {
		super(parentController);
		if(parentController instanceof DeleteBorrowersTransaction){
			operationType = "Delete";
		}else if(parentController instanceof ModifyBorrowersTransaction){
			operationType = "Modify";
		}
	}

	@Override
	public void execute() {
		showView("ListBorrowersView");
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.BORROWER_COLLECTION)) {
			return borrowers;
		}else if(key.equals(Key.SELECT_BORROWER)){
			return selectedBorrower;
		}else if(key.equals(Key.OPERATION_TYPE)){
			return operationType;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.BORROWER_COLLECTION)){
			getBorrowers((Properties)value);
		}else if(key.equals(Key.SELECT_BORROWER)){
			selectedBorrower = (Borrower)value;
		}
		super.stateChangeRequest(key, value);
	}
	
	/**
	 * Fetches borrowers that match searchCriteria 
	 * @param searchCriteria
	 */
	private void getBorrowers(Properties searchCriteria){
		BorrowerCollection borrowerCollection = new BorrowerCollection();
		borrowerCollection.findLike(searchCriteria);
		borrowers = borrowerCollection.getEntities();
	}		
}
