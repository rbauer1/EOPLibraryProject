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

import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;

import common.Mailer;

import controller.Controller;
import exception.InvalidPrimaryKeyException;

public class RecoverPasswordTransaction extends Transaction {
	
	/** Worker Model this transaction is reseting the password for */
	private Worker worker;
	
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
		if(key.equals("BannerID")){
			return bannerId;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SEND_RESET_CODE)){
			sendPasswordResetToken((Properties) value);
		}else if(key.equals(Key.RESET_PASSWORD)){
			resetPassword((Properties) value);	
		}else if(key.equals("BannerID")){
			bannerId = (String) value;
		}
		super.stateChangeRequest(key, value);
	}

	private void sendPasswordResetToken(final Properties workerData){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				try {
					worker = new Worker(workerData.getProperty("BannerID", ""));
				} catch (InvalidPrimaryKeyException e) {
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! Looks like you provided an invalid Banner Id."));
					return false;
				}
				String resetCode = worker.createPasswordResetCode();
				String email = (String)worker.getState("Email");
				String name = worker.getState("FirstName") + " " + worker.getState("LastName");
				Mailer.getInstance().send(email, "EOP Library Password Reset", createResetCodeEmail(name, resetCode));
				return true;
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
					showView("ResetPasswordView");
				}
			}
		}.execute();
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
	
	private void resetPassword(final Properties resetPasswordData){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				resetPasswordData.setProperty("PasswordChangeType", "CodeReset");
				worker.stateChangeRequest(resetPasswordData);
				return worker.save();
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
					parentController.stateChangeRequest(Key.WORKER, worker);
					stateChangeRequest(Key.DISPLAY_MAIN_MENU, worker);
					parentController.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! Your password was successfully changed."));
				}else{
					List<String> inputErrors = worker.getErrors();
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
