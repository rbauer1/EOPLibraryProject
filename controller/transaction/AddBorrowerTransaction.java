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
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles adding a new borrower.
 */
public class AddBorrowerTransaction extends Transaction {
	
	/** List of errors in the input */
	private List<String> inputErrors;

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
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SAVE_BORROWER)){
			addBorrower((Properties) value);
		}
		registry.updateSubscribers(key, this);
	}

	/**
	 * Creates borrower with provided data and saves it in db.
	 * @param borrowerData
	 */
	private void addBorrower(Properties borrowerData){
		Borrower borrower = new Borrower(borrowerData);
		if(borrower.save()){
			stateChangeRequest(Key.SAVE_SUCCESS, null);
		}else{
			inputErrors = borrower.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.INPUT_ERROR, null);
			}else{
				stateChangeRequest(Key.SAVE_ERROR, null);
			}
		}
	}
}
