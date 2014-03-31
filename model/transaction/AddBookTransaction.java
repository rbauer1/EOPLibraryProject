/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package model.transaction;

import java.util.Properties;

import model.Book;
import model.Worker;
import userinterface.View;
import exception.InvalidPrimaryKeyException;

public class AddBookTransaction extends Transaction {
	private Book book;
	private String resetCode;

	public AddBookTransaction() {
		super();
	}

	@Override
	public Object getState(String key) {
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals("RequestResetToken")){
			sendPasswordResetToken((Properties) value);
		}else if(key.equals("ResetPassword")){
			resetPassword((Properties) value);
		}
		registry.updateSubscribers(key, this);
	}

	@Override
	protected void setDependencies() {
		Properties dependencies = new Properties();
		registry.setDependencies(dependencies);
	}

	@Override
	protected View createView() {
		return getView("AddBookView");
	}

	private void sendPasswordResetToken(Properties workerData){
		try {
			book = new Book(workerData.getProperty("Barcode"));
			String title = (String)book.getState("Title");
			String author = (String)book.getState("FirstName") + " " + (String)book.getState("LastName");
			resetCode = book.getResetToken();
			swapToView(getView("PasswordResetView"));
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest("InputError", "Invalid Banner Id.");
		}
	}
	
	private void resetPassword(Properties passwordData){
		String password = passwordData.getProperty("Password");
		if(!resetCode.equals(passwordData.getProperty("ResetCode"))){
			stateChangeRequest("InputError", "Invalid reset code.");
		}
		if(password.length() < 6){
			stateChangeRequest("InputError", "Password must be greater than 5 characters long!");
		}
		if(!password.equals(passwordData.getProperty("PasswordConfirmation"))){
			stateChangeRequest("InputError", "Password and password confirmation must match.");
		}
		book.stateChangeRequest("Password", password);
		book.save();
		stateChangeRequest("RecoverPasswordTransactionCompleted", null);
	}
}
