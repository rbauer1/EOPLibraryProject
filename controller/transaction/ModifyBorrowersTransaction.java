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


import java.util.Properties;

import model.Borrower;
import utilities.Key;
import controller.Controller;

public class ModifyBorrowersTransaction extends Transaction {
	
	/** Book Model this transaction is updating */
	private Borrower borrower;
	
	/**
	 * Constructs Modify Borrower Transaction
	 * @param parentController
	 */
	public ModifyBorrowersTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public void execute(){
		TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", 
						Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER, Key.MODIFY_OR_DELETE);
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.SELECT_BORROWER)){
			return borrower;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			borrower = (Borrower)value;
			showView("ModifyBorrowerView");
		}else if(key.equals(Key.SUBMIT_BORROWER)){
			updateBook((Properties)value);
		}
		registry.updateSubscribers(key, this);
	}
	
	/**
	 * Updates selected borrower with provided data
	 * @param borrowerData
	 */
	private void updateBook(Properties borrowerData){
		borrower.stateChangeRequest(borrowerData);
		if(borrower.save()){
			stateChangeRequest(Key.SAVE_SUCCESS, null);
		}else{
			stateChangeRequest(Key.INPUT_ERROR, null);
		}
	}
}	
