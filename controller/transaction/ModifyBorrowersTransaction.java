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
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles modifying a new borrower.
 */
public class ModifyBorrowersTransaction extends Transaction {

	/** Borrower Model this transaction is updating */
	private Borrower borrower;

	/** Transaction for listing borrowers */
	private Transaction listBorrowersTransaction;

	/**
	 * Constructs Modify Borrowers Transaction
	 * @param parentController
	 */
	public ModifyBorrowersTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute(){
		listBorrowersTransaction = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
		listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a borrower from the list below to modify."));
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BORROWER, Key.BORROWER);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.BORROWER);
		return dependencies;
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			borrower = (Borrower)value;
			showView("ModifyBorrowerView");
		}else if(key.equals(Key.SAVE_BORROWER)){
			updateBorrower((Properties)value);
		}else if(key.equals(Key.RELOAD_ENTITY)){
			borrower.reload();
		}else if(key.equals(Key.BACK)){
			listBorrowersTransaction.execute();
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a borrower from the list below to modify."));
		}
		super.stateChangeRequest(key, value);
	}

	/**
	 * Updates selected borrower with provided data
	 * @param borrowerData
	 */
	private void updateBorrower(Properties borrowerData){
		borrowerData.setProperty("Status", "Active");
		borrower.stateChangeRequest(borrowerData);
		if(borrower.save()){
			stateChangeRequest(Key.BACK, "ListBorrowersView");
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The borrower was sucessfully added."));
		}else{
			List<String> inputErrors = borrower.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
			}
		}
	}

}
