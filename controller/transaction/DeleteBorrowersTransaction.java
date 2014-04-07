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


import javax.swing.JOptionPane;

import model.Borrower;
import utilities.Key;
import controller.Controller;

public class DeleteBorrowersTransaction extends Transaction {
	
	private Transaction listBorrowersTransaction;

	/**
	 * Constructs Delete Borrowers Transaction
	 * @param parentController
	 */
	public DeleteBorrowersTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public Object getState(String key) {
		return null;
	}
	
	@Override
	public void execute(){
		listBorrowersTransaction = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			setBorrowerInactive((Borrower)value);
		}
		registry.updateSubscribers(key, this);
	}
	
	private void setBorrowerInactive(Borrower borrower){
		if(deleteConfirmationPopup() == JOptionPane.YES_OPTION){
			borrower.setInactive();//TODO handle delete error
			listBorrowersTransaction.stateChangeRequest(Key.REFRESH_LIST, null);
		}
	}
	
	private int deleteConfirmationPopup(){
		String message = "ATTENTION: You are about to delete a borrower from the system.\n" +
			"Are you sure you have selected the correct borrower and want to proceed?";
		return JOptionPane.showConfirmDialog(frame, message, "Borrower will be deleted", JOptionPane.YES_NO_OPTION);

	}
}
