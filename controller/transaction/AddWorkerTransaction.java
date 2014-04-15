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
 * Transaction that handles adding a new worker.
 */
public class AddWorkerTransaction extends Transaction {

	/**
	 * Constructs Add Worker Transaction
	 * @param parentController
	 */
	public AddWorkerTransaction(Controller parentController) {
		super(parentController);
	}


	/**
	 * Creates worker with provided data and saves it in db.
	 * @param workerData
	 */
	private void addWorker(Properties workerData){
		Worker worker = new Worker(workerData);
		if(worker.save()){
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The worker was sucessfully added."));
		}else{
			List<String> inputErrors = worker.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
			}
		}
	}

	@Override
	public void execute() {
		showView("AddWorkerView");
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SAVE_WORKER)){
			addWorker((Properties) value);
		}
		super.stateChangeRequest(key, value);
	}
}
