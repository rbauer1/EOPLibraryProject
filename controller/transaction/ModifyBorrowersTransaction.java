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
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Borrower;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles modifying a new borrower.
 */
public class ModifyBorrowersTransaction extends Transaction {

	/** Borrower Model this transaction is updating */
	private Borrower borrower;

	/** Transaction for listing borrowers */
	private Transaction listBorrowersTransaction;

	/**
	 * Constructs Modify Borrowers Transaction
	 * @param parentController
	 */
	public ModifyBorrowersTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute(){
		listBorrowersTransaction = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
		listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a borrower from the list below to modify."));
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BORROWER, Key.BORROWER);
		return dependencies;
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		return super.getState(key);
	}

	/**
	 * Reload the borrower from the db
	 */
	private void reloadBorrower() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				borrower.reload();
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.BORROWER, null);
			}
		}.execute();
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			borrower = (Borrower)value;
			showView("ModifyBorrowerView");
		}else if(key.equals(Key.SAVE_BORROWER)){
			updateBorrower((Properties)value);
		}else if(key.equals(Key.RELOAD_ENTITY)){
			reloadBorrower();
		}else if(key.equals(Key.BACK)){
			listBorrowersTransaction.execute();
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a borrower from the list below to modify."));
		}
		super.stateChangeRequest(key, value);
	}

	/**
	 * Updates selected borrower with provided data
	 * @param borrowerData
	 */
	private void updateBorrower(final Properties borrowerData){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				borrowerData.setProperty("Status", "Active");
				borrower.stateChangeRequest(borrowerData);
				return borrower.save();
			}

			@Override
			public void done() {
				boolean success = false;
				try {
					success = get();
				} catch (InterruptedException e) {
					success = false;
				} catch (ExecutionException e) {
					success = false;
				}
				if(success){
					stateChangeRequest(Key.BACK, "ListBorrowersView");
					listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The borrower was sucessfully saved."));
				}else{
					List<String> inputErrors = borrower.getErrors();
					if(inputErrors.size() > 0){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
					}else{
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
					}
				}
			}
		}.execute();
	}

}
