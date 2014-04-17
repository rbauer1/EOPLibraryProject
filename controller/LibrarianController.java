/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package controller;

import java.util.Properties;

import model.Worker;
import utilities.Key;
import controller.transaction.Transaction;
import controller.transaction.TransactionFactory;
import exception.InvalidPrimaryKeyException;

/**
 * Librarian represents the System controller or Main Interface Agent
 */
public class LibrarianController extends Controller {

	private String loginErrorMessage = "";

	private Worker worker;

	public LibrarianController() {
		super();
		showView("LoginView");
	}

	/**
	 * Provides the value associated with the provided key.
	 * @param key that references an attribute
	 * @return value associated with the key
	 */
	@Override
	public Object getState(String key) {
		if (key.equals(Key.INPUT_ERROR)) {
			return loginErrorMessage;
		}
		if (key.equals(Key.WORKER)){
			return worker;
		}
		return super.getState(key);
	}

	/**
	 * Tries to login a worker with the provided username and password.
	 * @param workerData
	 */
	protected void loginWorker(Properties workerData) {
		try {
			worker = new Worker(workerData.getProperty("BannerID",""));
			if(!worker.validPassword(workerData.getProperty("Password",""))){
				stateChangeRequest(Key.INPUT_ERROR, "Invalid Banner Id or Password.");
			}else{
				showView("MainMenuView");
			}
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.INPUT_ERROR, "Invalid Banner Id or Password.");
		}
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		Transaction transaction;
		if (key.equals(Key.LOGIN)) {
			loginErrorMessage = "";
			loginWorker((Properties) value);
		} else if (key.equals(Key.INPUT_ERROR)) {
			loginErrorMessage = (String) value;

		} else if (key.equals(Key.DISPLAY_MAIN_MENU)) {
			showView("MainMenuView");
		} else if (key.equals(Key.DISPLAY_BOOK_MENU)) {
			showView("BookMenuView");
		} else if (key.equals(Key.DISPLAY_BORROWER_MENU)) {
			showView("BorrowerMenuView");
		} else if (key.equals(Key.DISPLAY_WORKER_MENU)) {
			showView("WorkerMenuView");
		} else if (key.equals(Key.DISPLAY_LOGIN)) {
			showView("LoginView");

		} else if (key.equals(Key.EXECUTE_ADD_BOOK)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_MODIFY_BOOK)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_DELETE_BOOK)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_PROCESS_LOST_BOOK)){
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_CHECKOUT_BOOK)){
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_MAIN_MENU, Key.MESSAGE);


		} else if (key.equals(Key.EXECUTE_ADD_BORROWER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);
		} else if (key.equals(Key.EXECUTE_MODIFY_BORROWER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);
		} else if (key.equals(Key.EXECUTE_DELETE_BORROWER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);

		} else if (key.equals(Key.EXECUTE_ADD_WORKER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_WORKER_MENU);
		} else if (key.equals(Key.EXECUTE_MODIFY_WORKER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_WORKER_MENU);
		} else if (key.equals(Key.EXECUTE_DELETE_WORKER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_WORKER_MENU);

		} else if (key.equals(Key.EXECUTE_RECOVER_PASSWORD)){
			transaction = TransactionFactory.executeTransaction(this, key, Key.DISPLAY_MAIN_MENU, Key.DISPLAY_LOGIN);
			transaction.stateChangeRequest("BannerID", value);

		} else if (key.endsWith("Transaction")){
			TransactionFactory.executeTransaction(this, key);
		} else if (key.equals(Key.LOGOUT)) {
			showView("LoginView");
		}
		super.stateChangeRequest(key, value);
	}

}