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
 * Transacation responsible for deleting borrowers
 */
public class DeleteBorrowersTransaction extends Transaction {
	
	/** Transaction for listing borrowers */
	private Transaction listBorrowersTransaction;
	
	/** Borrower Model this transaction is updating */
	private Borrower borrower;	
	
	/**
	 * Constructs Delete Borrowers Transaction
	 * @param parentController
	 */
	public DeleteBorrowersTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BORROWER, Key.BORROWER);
		return dependencies;
	}
	
	@Override
	public void execute(){
		listBorrowersTransaction = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
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
			showView("DeleteBorrowerView");
		}else if(key.equals(Key.SAVE_BORROWER)){
			deleteBorrower((Properties)value);
		}
		super.stateChangeRequest(key, value);
	}
	
	private void deleteBorrower(Properties borrowerData){
		String notes = "Reason For Deletion: " + borrowerData.getProperty("DeletionReason", "None") + "\n";
		notes += borrowerData.getProperty("Notes", "");
		if(borrower.setInactive(notes)){
			listBorrowersTransaction.execute();
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The borrower was deleted successfully."));
		}else{
			List<String> inputErrors = borrower.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while deleting."));
			}
		}
	}
}
