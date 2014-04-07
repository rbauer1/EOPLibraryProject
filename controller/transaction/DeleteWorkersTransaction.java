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

import model.Worker;
import utilities.Key;
import controller.Controller;

public class DeleteWorkersTransaction extends Transaction {
	
	private Transaction listWorkersTransaction;

	/**
	 * Constructs Delete Workers Transaction
	 * @param parentController
	 */
	public DeleteWorkersTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public Object getState(String key) {
		return null;
	}
	
	@Override
	public void execute(){
		listWorkersTransaction = TransactionFactory.executeTransaction(this, "ListWorkersTransaction", Key.DISPLAY_WORKER_MENU, Key.SELECT_WORKER);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_WORKER)){
			setWorkerInactive((Worker)value);
		}
		registry.updateSubscribers(key, this);
	}
	
	private void setWorkerInactive(Worker worker){
		if(deleteConfirmationPopup() == JOptionPane.YES_OPTION){
			worker.setInactive();//TODO handle delete error
			listWorkersTransaction.stateChangeRequest(Key.REFRESH_LIST, null);
		}
	}
	
	private int deleteConfirmationPopup(){
		String message = "ATTENTION: You are about to delete a worker from the system.\n" +
			"Are you sure you have selected the correct worker and want to proceed?";
		return JOptionPane.showConfirmDialog(frame, message, "Worker will be deleted", JOptionPane.YES_NO_OPTION);

	}
}
