/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.view;

import java.util.Properties;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import userinterface.ViewHelper;
import userinterface.component.PasswordField;
import userinterface.component.TextField;
import utilities.Key;
import controller.Controller;

/**
 * The Password Reset View for the EOP Library application. Provides the
 * interface for the workers to reset their password with the reset token emailed to them.
 */
public class PasswordResetView extends View {
	
	private static final long serialVersionUID = -7309149910050009529L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Cancel"};
	
	/** Data entry fields */
	private JTextField resetCodeField;
	private JPasswordField passwordField;
	private JPasswordField passwordConfirmationField;

	/**
	 * Constructs Reset Password view 
	 * @param controller
	 */
	public PasswordResetView(Controller controller) {
		super(controller, "Set Password", BUTTON_NAMES);
		subscribeToController(Key.INPUT_ERROR);
	}

	@Override
	protected void build() {
		resetCodeField = new TextField(25);
		add(ViewHelper.formatFieldCenter("Reset Code", resetCodeField));
		
		passwordField = new PasswordField(25);
		add(ViewHelper.formatFieldCenter("New Password", passwordField));
		
		passwordConfirmationField = new PasswordField(25);
		add(ViewHelper.formatFieldCenter("Confirm Password", passwordConfirmationField));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Cancel")) {
			controller.stateChangeRequest(Key.RECOVER_PW_COMPLETED, null);
		} else if (source == buttons.get("Cancel")) {
			String resetCode = this.resetCodeField.getText();
			String password = new String(this.passwordField.getPassword());
			String passwordConfirmation = new String(this.passwordConfirmationField.getPassword());
			
			if (validate(resetCode, password, passwordConfirmation)) {
				Properties passwordData = new Properties();
				passwordData.setProperty("ResetCode", resetCode);
				passwordData.setProperty("Password", password);
				passwordData.setProperty("PasswordConfirmation", passwordConfirmation);
				controller.stateChangeRequest(Key.RESET_PW, passwordData);
			}

		}

	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage(value.toString());
		}
	}
	
	@Override
	public void afterShown(){
		resetCodeField.requestFocusInWindow();
	}
	
	private boolean validate(String resetCode, String password, String passwordConfirm) {
		if ((resetCode == null) || (resetCode.length() == 0)) {
			messagePanel.displayErrorMessage("Enter the reset code emailed to you!");
			return false;
		}
		if ((password == null) || (password.length() < 6)) {
			messagePanel.displayErrorMessage("Password must be greater than 5 characters long!");
			return false;
		}
		if (!password.equals(passwordConfirm)) {
			messagePanel.displayErrorMessage("Password must match confirmation password!");
			return false;
		}
		return true;
	}
}
