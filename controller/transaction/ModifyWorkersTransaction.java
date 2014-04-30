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
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles modifying a new worker.
 */
public class ModifyWorkersTransaction extends Transaction {

	/** Transaction for listing workers */
	private Transaction listWorkersTransaction;

	/** Worker Model this transaction is updating */
	private Worker worker;

	/**
	 * Constructs Modify Workers Transaction
	 * @param parentController
	 */
	public ModifyWorkersTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute(){
		listWorkersTransaction = TransactionFactory.executeTransaction(this, "ListWorkersTransaction", Key.DISPLAY_WORKER_MENU, Key.SELECT_WORKER);
		listWorkersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a worker from the list below to modify."));
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_WORKER, Key.WORKER);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.WORKER);
		return dependencies;
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
			showView("ModifyWorkerView");
		}else if(key.equals(Key.SAVE_WORKER)){
			updateWorker((Properties)value);
		}else if(key.equals(Key.RELOAD_ENTITY)){
			worker.reload();
		}else if(key.equals(Key.BACK)){
			listWorkersTransaction.execute();
			listWorkersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a worker from the list below to modify."));
		}
		super.stateChangeRequest(key, value);
	}

	/**
	 * Updates selected worker with provided data
	 * @param workerData
	 */
	private void updateWorker(Properties workerData){
		workerData.setProperty("Status", "Active");
		worker.stateChangeRequest(workerData);
		if(worker.save()){
			stateChangeRequest(Key.BACK, "ListWorkersView");
			listWorkersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The worker was sucessfully added."));
		}else{
			List<String> inputErrors = worker.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
			}
		}
	}

}
