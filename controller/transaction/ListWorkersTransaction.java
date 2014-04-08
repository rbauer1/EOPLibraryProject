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
import model.WorkerCollection;
import utilities.Key;
import controller.Controller;

public class ListWorkersTransaction extends Transaction {
	private WorkerCollection workerCollection;
	private List<Worker> workers;
	private Worker selectedWorker;
	private String operationType;

	/**
	 * Constructs List Workers Transaction
	 * @param parentController
	 */
	public ListWorkersTransaction(Controller parentController) {
		super(parentController);
		if(parentController instanceof DeleteWorkersTransaction){
			operationType = "Delete";
		}
		else if(parentController instanceof ModifyWorkersTransaction){
			operationType = "Modify";
		}
	}

	@Override
	public void execute() {
		showView("ListWorkersView");
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.GET_WORKER_COLLECTION)) {
			return workers;
		}else if(key.equals(Key.SELECT_WORKER)){
			return selectedWorker;
		}else if(key.equals(Key.OPERATION_TYPE)){
			return operationType;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.GET_WORKER_COLLECTION)){
			getBorrowers((Properties)value);
		}else if(key.equals(Key.SELECT_WORKER)){
			selectedWorker = (Worker)value;
		}
		registry.updateSubscribers(key, this);
	}
	
	private void getBorrowers(Properties props){
		workerCollection = new WorkerCollection();
		workerCollection.findLike(props);
		workers = workerCollection.getEntities();
	}
		
}
