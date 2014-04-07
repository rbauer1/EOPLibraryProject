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

public class AddBorrowerTransaction extends Transaction {
	
	/** Book Model this transaction is adding */
	private Borrower borrower;

	/**
	 * Constructs Add Borrower Transaction
	 * @param parentController
	 */
	public AddBorrowerTransaction(Controller parentController) {
		super(parentController);
	}
	
	
	@Override
	public void execute() {
		showView("AddBorrowerView");
	}

	@Override
	public Object getState(String key) {
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SUBMIT_BORROWER)){
			addBorrower((Properties) value);
		}
		registry.updateSubscribers(key, this);
	}


	/**
	 * Creates book with provided data and saves it in db.
	 * @param borrowerData
	 */
	private void addBorrower(Properties borrowerData){
		borrower = new Borrower(borrowerData);
		if(borrower.save()){
			stateChangeRequest(Key.BORROWER_SUBMIT_SUCCESS, null);
		}else{
			stateChangeRequest(Key.INPUT_ERROR, null);
		}
	}
	
}
