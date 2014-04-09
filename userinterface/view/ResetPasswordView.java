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

import userinterface.view.form.Form;
import userinterface.view.form.ResetPasswordForm;
import utilities.Key;
import controller.Controller;

/**
 * The Password Reset View for the EOP Library application. Provides the
 * interface for the workers to reset their password with the reset token emailed to them.
 */
public class ResetPasswordView extends View {
	
	private static final long serialVersionUID = -7309149910050009529L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Cancel"};
	
	/** Form to take in data */
	private Form form;

	/**
	 * Constructs Reset Password view 
	 * @param controller
	 */
	public ResetPasswordView(Controller controller) {
		super(controller, "Set Password", BUTTON_NAMES);
		subscribeToController(Key.INPUT_ERROR);
	}

	@Override
	protected void build() {
		form = new ResetPasswordForm(this);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Cancel")) {
			controller.stateChangeRequest(Key.RECOVER_PW_COMPLETED, null);
		} else if (source == buttons.get("Cancel")) {
			controller.stateChangeRequest(Key.RESET_PW, form.getValues());
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
		form.requestFocusForDefaultField();
	}
}
