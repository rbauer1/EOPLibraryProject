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
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Borrower;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles adding a new borrower.
 */
public class AddBorrowerTransaction extends Transaction {

	/**
	 * Constructs Add Borrower Transaction
	 * @param parentController
	 */
	public AddBorrowerTransaction(Controller parentController) {
		super(parentController);
	}


	/**
	 * Creates borrower with provided data and saves it in db.
	 * @param borrowerData
	 */
	private void addBorrower(final Properties borrowerData){
		new SwingWorker<Boolean, Void>() {

			private Borrower borrower;

			@Override
			protected Boolean doInBackground() {
				borrower = new Borrower(borrowerData);
				return borrower.save();
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
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The borrower was sucessfully added."));
				}else{
					List<String> inputErrors = borrower.getErrors();
					if(inputErrors.size() > 0){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
					}else{
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
					}
				}
			}
		}.execute();
	}

	@Override
	public void execute() {
		showView("AddBorrowerView");
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SAVE_BORROWER)){
			addBorrower((Properties) value);
		}
		super.stateChangeRequest(key, value);
	}
}
