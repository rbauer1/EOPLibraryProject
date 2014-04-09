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

import common.Mailer;

import controller.Controller;
import exception.InvalidPrimaryKeyException;

public class RecoverPasswordTransaction extends Transaction {
	
	/** Worker Model this transaction is reseting the password for */
	private Worker worker;
	
	/** Reset Code generated for the worker */
	private String resetCode;
	
	private String errorMessage;
	
	private boolean passwordResetSuccess = false;
	
	/**
	 * Constructs Recover Password Transaction
	 * @param parentController
	 */
	public RecoverPasswordTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public void execute() {
		showView("ForgotPasswordView");
	}
	

	@Override
	public Object getState(String key) {
		if(key.equals(Key.RECOVER_PW_COMPLETED)){
			return passwordResetSuccess;
		}else if(key.equals(Key.INPUT_ERROR)){
			return errorMessage;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.REQUEST_RESET_TOKEN)){
			sendPasswordResetToken((Properties) value);
		}else if(key.equals(Key.RESET_PW)){
			resetPassword((Properties) value);
		}else if(key.equals(Key.RECOVER_PW_COMPLETED)){
			if(value!=null){ //TODO why null check?
				passwordResetSuccess = (Boolean)value;
			}			
		}else if(key.equals(Key.INPUT_ERROR)){
			errorMessage = (String)value;
		}
		registry.updateSubscribers(key, this);
	}

	private void sendPasswordResetToken(Properties workerData){
		
		try {
			String bannerId = workerData.getProperty("BannerID", "");
			worker = new Worker(bannerId);
			Mailer mailer = new Mailer();
			String email = (String)worker.getState("Email");
			String name = (String)worker.getState("FirstName") + " " + (String)worker.getState("LastName");
			resetCode = worker.getResetToken();
			mailer.send(email, "EOP Library Password Reset", getResetCodeEmail(name, resetCode));
			showView("ResetPasswordView");
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.INPUT_ERROR, "Invalid Banner Id.");
		}
	}
	
	private String getResetCodeEmail(String name, String resetCode){
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>EOP Library System</h1>");
		sb.append("<h2>Password Reset</h2>");
		sb.append("<p>A password reset was requested for " + name + ".</p>");
		sb.append("<p>To reset password, use the following reset code:</p>");
		sb.append("<p>" + resetCode + "</p>");
		return sb.toString();
	}
	
	private void resetPassword(Properties passwordData){
		String password = passwordData.getProperty("Password");
		if(!resetCode.equals(passwordData.getProperty("ResetCode"))){
			stateChangeRequest(Key.INPUT_ERROR, "Invalid reset code.");
		}else if(password.length() < 6){
			stateChangeRequest(Key.INPUT_ERROR, "Password must be greater than 5 characters long!");
		}else if(!password.equals(passwordData.getProperty("PasswordConfirmation"))){
			stateChangeRequest(Key.INPUT_ERROR, "Password and password confirmation must match.");
		}else{
			worker.stateChangeRequest(Key.PW, password);
			worker.save();
			stateChangeRequest(Key.RECOVER_PW_COMPLETED, true);
		}
	}
}
