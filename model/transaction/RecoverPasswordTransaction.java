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

import model.Worker;
import userinterface.View;
import common.Mailer;
import exception.InvalidPrimaryKeyException;

public class RecoverPasswordTransaction extends Transaction {
	private Worker worker;
	private String resetCode;

	public RecoverPasswordTransaction() {
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
		return getView("ForgotPasswordView");
	}

	private void sendPasswordResetToken(Properties workerData){
		try {
			worker = new Worker(workerData.getProperty("BannerID"));
			Mailer mailer = new Mailer();
			String email = (String)worker.getState("Email");
			String name = (String)worker.getState("FirstName") + " " + (String)worker.getState("LastName");
			resetCode = worker.getResetToken();
			StringBuilder sb = new StringBuilder();
			sb.append("<h1>EOP Library System</h1>");
			sb.append("<h2>Password Reset</h2>");
			sb.append("<p>A password reset was requested for " + name + ".</p>");
			sb.append("<p>To reset password, use the following reset code:</p>");
			sb.append("<p>" + resetCode + "</p>");
			mailer.send(email, "EOP Library Password Reset", sb.toString());
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
		worker.stateChangeRequest("Password", password);
		worker.save();
		stateChangeRequest("RecoverPasswordTransactionCompleted", null);
	}
}
