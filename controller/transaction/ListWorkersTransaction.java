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

import javax.swing.SwingWorker;

import model.Worker;
import model.WorkerCollection;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles listing and selecting a worker
 */
public class ListWorkersTransaction extends Transaction {

	/** type of operation, can be Delete or Modify */
	private String operationType;

	/** worker selected from list */
	private Worker selectedWorker;

	/** list of workers returned from search */
	private List<Worker> workers;

	/**
	 * Constructs List Workers Transaction
	 * @param parentController
	 */
	public ListWorkersTransaction(Controller parentController) {
		super(parentController);
		if(parentController instanceof DeleteWorkersTransaction){
			operationType = "Delete";
		}else if(parentController instanceof ModifyWorkersTransaction){
			operationType = "Modify";
		}else{
			operationType = "Select";
		}
	}

	@Override
	public void execute() {
		showView("ListWorkersView");
	}

	/**
	 * Filters the workers by the provided search criteria
	 * @param searchCriteria
	 */
	private void filter(final Properties searchCriteria) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				getWorkers(searchCriteria);
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.WORKER_COLLECTION, null);
			}
		}.execute();
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.WORKER_COLLECTION)) {
			return workers;
		}else if(key.equals(Key.SELECT_WORKER)){
			return selectedWorker;
		}else if(key.equals(Key.OPERATION_TYPE)){
			return operationType;
		}
		return super.getState(key);
	}

	/**
	 * Fetches workers that match searchCriteria
	 * @param searchCriteria
	 */
	private void getWorkers(Properties searchCriteria){
		WorkerCollection workerCollection = new WorkerCollection();
		workerCollection.findLike(searchCriteria);
		workers = workerCollection.getEntities();
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.FILTER)){
			filter((Properties)value);
		}else if(key.equals(Key.SELECT_WORKER)){
			selectedWorker = (Worker)value;
		}
		super.stateChangeRequest(key, value);
	}
}
