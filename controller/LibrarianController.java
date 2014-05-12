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
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Worker;
import userinterface.MainFrame;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.transaction.Transaction;
import controller.transaction.TransactionFactory;
import exception.InvalidPrimaryKeyException;

/**
 * Librarian represents the System controller or Main Interface Agent
 */
public class LibrarianController extends Controller {

	/** currently signed in worker */
	private Worker worker;

	/**
	 * Constructs Main Controller and shows login
	 */
	public LibrarianController() {
		super();
		showView("LoginView");
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.LOGGED_IN_WORKER)){
			return worker;
		}
		return super.getState(key);
	}

	/**
	 * Tries to login a worker with the provided username and password.
	 * @param workerData
	 */
	private void loginWorker(final Properties workerData) {
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				try {
					worker = new Worker(workerData.getProperty("BannerID",""));
					return worker.validPassword(workerData.getProperty("Password",""));
				} catch (InvalidPrimaryKeyException e) {
					return false;
				}
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
					showView("MainMenuView");
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! You have successfully signed in."));
				}else{
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Invalid Banner Id or Password."));
				}
			}
		}.execute();
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		Transaction transaction;
		if (key.equals(Key.LOGIN)) {
			loginWorker((Properties) value);

		} else if (key.equals(Key.DISPLAY_MAIN_MENU)) {
			MainFrame.getInstance().getScreen().updateState(Key.RESET_MENU, null);
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
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU, Key.DISPLAY_MAIN_MENU);
		} else if (key.equals(Key.EXECUTE_RENT_BOOK)){
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_MAIN_MENU, Key.MESSAGE);
		} else if (key.equals(Key.EXECUTE_RETURN_BOOK)){
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_MAIN_MENU, Key.MESSAGE);
		} else if (key.equals(Key.EXECUTE_LIST_RENTED_BOOKS)){
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_LIST_AVAILABLE_BOOKS)){
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BOOK_MENU);
			
		} else if (key.equals(Key.EXECUTE_ADD_BORROWER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);
		} else if (key.equals(Key.EXECUTE_MODIFY_BORROWER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);
		} else if (key.equals(Key.EXECUTE_DELETE_BORROWER)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);
		} else if (key.equals(Key.EXECUTE_LIST_BORROWERS_WITH_RENTED_BOOKS)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_BORROWER_MENU);
		} else if (key.equals(Key.EXECUTE_DELINQUENCY_CHECK)) {
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

		} else if (key.equals(Key.EXECUTE_PRINT_PDF)) {
			TransactionFactory.executeTransaction(this, key, Key.DISPLAY_MAIN_MENU);

		} else if (key.endsWith("Transaction")){
			TransactionFactory.executeTransaction(this, key);
		} else if (key.equals(Key.LOGOUT)) {
			showView("LoginView");
			/*
			 * This is needed to update the menus between logins in case the next worker has different credentials
			 */
			views.clear();

		} else if (key.equals(Key.WORKER)) {
			worker = (Worker)value;
		}
		super.stateChangeRequest(key, value);
	}

}