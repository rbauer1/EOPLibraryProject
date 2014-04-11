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
import utilities.Key;

import common.Mailer;

import controller.Controller;
import exception.InvalidPrimaryKeyException;

public class RecoverPasswordTransaction extends Transaction {
	
	/** Worker Model this transaction is reseting the password for */
	private Worker worker;
	
	/** List of errors in the input */
	private List<String> inputErrorMessages;
	
	/** Message for input error */
	private String inputError;
	
	private String bannerId;
	
	/**
	 * Constructs Recover Password Transaction
	 * @param parentController
	 */
	public RecoverPasswordTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public void execute() {
		showView("SendPasswordResetCodeView");
	}
	

	@Override
	public Object getState(String key) {
		if(key.equals(Key.INPUT_ERROR)){
			return inputError;
		}
		if(key.equals(Key.INPUT_ERROR_MESSAGES)){
			return inputErrorMessages;
		}
		if(key.equals("BannerID")){
			return bannerId;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SEND_RESET_CODE)){
			sendPasswordResetToken((Properties) value);
		}else if(key.equals(Key.RESET_PASSWORD)){
			resetPassword((Properties) value);	
		}else if(key.equals(Key.INPUT_ERROR)){
			inputError = (String) value;
		}else if(key.equals("BannerID")){
			bannerId = (String) value;
		}
		registry.updateSubscribers(key, this);
	}

	private void sendPasswordResetToken(Properties workerData){
		try {
			worker = new Worker(workerData.getProperty("BannerID", ""));
			String resetCode = worker.createPasswordResetCode();
			String email = (String)worker.getState("Email");
			String name = worker.getState("FirstName") + " " + worker.getState("LastName");
			Mailer.getInstance().send(email, "EOP Library Password Reset", createResetCodeEmail(name, resetCode));
			showView("ResetPasswordView");
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.INPUT_ERROR, "Aww shucks! Looks like you provided an invalid Banner Id.");
		}
	}
	
	private String createResetCodeEmail(String name, String resetCode){
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>EOP Library System</h1>");
		sb.append("<h2>Password Reset</h2>");
		sb.append("<p>A password reset was requested for " + name + ".</p>");
		sb.append("<p>To reset password, use the following reset code:</p>");
		sb.append("<p>" + resetCode + "</p>");
		return sb.toString();
	}
	
	private void resetPassword(Properties resetPasswordData){
		resetPasswordData.setProperty("PasswordChangeType", "CodeReset");
		worker.stateChangeRequest(resetPasswordData);
		if(worker.save()){
			stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		}else{
			inputErrorMessages = worker.getErrors();
			if(inputErrorMessages.size() > 0){
				stateChangeRequest(Key.INPUT_ERROR, "Aw shucks! There are errors in the input. Please try again.");
			}else{
				stateChangeRequest(Key.SAVE_ERROR, null);
			}
		}
	}
}
