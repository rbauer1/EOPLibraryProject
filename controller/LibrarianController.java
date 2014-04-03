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

import controller.transaction.TransactionFactory;
import model.Worker;
import utilities.Key;
import exception.InvalidPrimaryKeyException;

/**
 * Librarian represents the System controller or Main Interface Agent
 */
public class LibrarianController extends Controller {

	protected String loginErrorMessage = "";
	
	public LibrarianController() {
		super();
		showView("LoginView");
	}
	
	/**
	 * Provides the value associated with the provided key.
	 * @param key that references an attribute
	 * @return value associated with the key
	 */
	public Object getState(String key) {
		if (key.equals(Key.LOGIN_ERROR)) {
			return loginErrorMessage;
		}
		throw new IllegalArgumentException("Unknown key: " + key);
	}
	
	public void stateChangeRequest(String key, Object value) {
		if (key.equals(Key.LOGIN)) {
			loginErrorMessage = "";
			loginWorker((Properties) value);
			//TODO Possibly start using TO_BOOK_MENU in place of XXX_COMPLETED for Book transactions
			
		} else if (key.equals(Key.LOGIN_ERROR)) {			
			loginErrorMessage = value.toString();
		} else if (key.equals(Key.BACK_TO_MAIN_MENU)) {		
			showView("MainMenuView");
		} else if (key.equals(Key.BACK_TO_BOOK_MENU)) {		
			showView("BookMenuView");
		} else if (key.equals(Key.EXECUTE_ADD_BOOK)) {		
			TransactionFactory.executeTransaction(this, key, Key.BACK_TO_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_MODIFY_BOOK)) {	
			TransactionFactory.executeTransaction(this, key, Key.BACK_TO_BOOK_MENU);//TODO needs , Key.MODIFY_OR_DELETE?
		} else if (key.equals(Key.EXECUTE_DELETE_BOOK)) {	
			TransactionFactory.executeTransaction(this, key, Key.BACK_TO_BOOK_MENU, Key.MODIFY_OR_DELETE);
		} else if (key.equals(Key.EXECUTE_RECOVER_PW)){		
			TransactionFactory.executeTransaction(this, key, Key.RECOVER_PW_COMPLETED);
		} else if (key.equals(Key.RECOVER_PW_COMPLETED)){	
			showView("LoginView");
		} else if (key.endsWith("Transaction")){			
			TransactionFactory.executeTransaction(this, key);
		} else if (key.equals(Key.LOGOUT)) {				
			showView("LoginView");
		} else if (key.equals(Key.EXIT_SYSTEM)) {			
			exitSystem();
		}
		registry.updateSubscribers(key, this);
	}
	
	/**
	 * Exits from the system, and is likely to be called as a result of
	 * a click on a button labeled something like 'Exit Application'.
	 */
	protected void exitSystem() {
		System.exit(0);
	}
		
	/**
	 * Tries to login a worker with the provided username and password.
	 * @param workerData
	 */
	protected void loginWorker(Properties workerData) {
		try {
			Worker worker = new Worker(workerData.getProperty("BannerID"));
			if(!worker.validPassword(workerData.getProperty("Password"))){
				stateChangeRequest(Key.LOGIN_ERROR, "Invalid Banner Id or Password.");
			}else{
				System.out.println("login success");
				showView("MainMenuView");
			}
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.LOGIN_ERROR, "Invalid Banner Id or Password.");
		}		
	}
	
}