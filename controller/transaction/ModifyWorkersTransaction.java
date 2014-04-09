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
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles modifying a new worker.
 */
public class ModifyWorkersTransaction extends Transaction {
	
	/** Worker Model this transaction is updating */
	private Worker worker;
	
	/** List of errors in the input */
	private List<String> inputErrors;
	
	/**
	 * Constructs Modify Workers Transaction
	 * @param parentController
	 */
	public ModifyWorkersTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_WORKER, Key.WORKER);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.WORKER);
		return dependencies;
	}
	
	@Override
	public void execute(){
		TransactionFactory.executeTransaction(this, "ListWorkersTransaction", Key.DISPLAY_WORKER_MENU, Key.SELECT_WORKER);
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.WORKER)){
			return worker;
		}
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
		}
		return null;
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
		}
		registry.updateSubscribers(key, this);
	}
	
	/**
	 * Updates selected worker with provided data
	 * @param workerData
	 */
	private void updateWorker(Properties workerData){
		worker.stateChangeRequest(workerData);
		if(worker.save()){
			stateChangeRequest(Key.SAVE_SUCCESS, null);
		}else{
			inputErrors = worker.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.INPUT_ERROR, null);
			}else{
				stateChangeRequest(Key.SAVE_ERROR, null);
			}
		}
	}
	
}	
