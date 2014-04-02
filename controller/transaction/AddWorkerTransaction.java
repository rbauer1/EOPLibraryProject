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

import model.Worker;
import utilities.Key;
import controller.Controller;

public class AddWorkerTransaction extends Transaction {
	
	/** Worker Model this transaction is adding */
	private Worker worker;

	/**
	 * Constructs Add Worker Transaction
	 * @param parentController
	 */
	public AddWorkerTransaction(Controller parentController) {
		super(parentController);
	}
	
	
	@Override
	public void execute() {
		showView("AddWorkerView");
	}

	@Override
	public Object getState(String key) {
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SUBMIT_WORKER)){
			addWorker((Properties) value);
		}
		registry.updateSubscribers(key, this);
	}


	/**
	 * Creates worker with provided data and saves it in db.
	 * @param workerData
	 */
	private void addWorker(Properties workerData){
		worker = new Worker(workerData);
		//TODO handle encrypting password more gracefully in model
		worker.stateChangeRequest("Password", workerData.getProperty("Password"));
		if(worker.save()){
			stateChangeRequest(Key.WORKER_SUBMIT_SUCCESS, null);
		}else{
			stateChangeRequest(Key.INPUT_ERROR, null);
		}
	}
	
}
