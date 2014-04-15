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

import model.Worker;
import userinterface.message.MessageEvent;
import utilities.Key;
import controller.Controller;

/**
 * Transacation responsible for deleting workers
 */
public class DeleteWorkersTransaction extends Transaction {
	
	/** Transaction for listing workers */
	private Transaction listWorkersTransaction;
	
	/** Worker Model this transaction is updating */
	private Worker worker;	
	
	/**
	 * Constructs Delete Workers Transaction
	 * @param parentController
	 */
	public DeleteWorkersTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_WORKER, Key.WORKER);
		return dependencies;
	}
	
	@Override
	public void execute(){
		listWorkersTransaction = TransactionFactory.executeTransaction(this, "ListWorkersTransaction", Key.DISPLAY_WORKER_MENU, Key.SELECT_WORKER);
	}
	
	@Override
	public Object getState(String key) {
		if(key.equals(Key.WORKER)){
			return worker;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_WORKER)){
			worker = (Worker)value;
			showView("DeleteWorkerView");
		}else if(key.equals(Key.SAVE_WORKER)){
			deleteWorker((Properties)value);
		}
		super.stateChangeRequest(key, value);
	}
	
	private void deleteWorker(Properties workerData){
		String notes = "Reason For Deletion: " + workerData.getProperty("DeletionReason", "None") + "\n";
		notes += workerData.getProperty("Notes", "");
		if(worker.setInactive(notes)){
			listWorkersTransaction.execute();
			listWorkersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent("Success", "Good Job! The worker was deleted successfully."));
		}else{
			List<String> inputErrors = worker.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent("Error", "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent("Error", "Whoops! An error occurred while deleting."));
			}
		}
	}
}
