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

import userinterface.message.MessageEvent;
import userinterface.view.form.BannerIdForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * The Forgot Password View for the EOP Library application. Provides the
 * interface for the workers to recover their password to the system.
 */
public class SendPasswordResetCodeView extends View {
	
	private static final long serialVersionUID = 1169974525395804659L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Cancel"};

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs Forgot Password view
	 * @param controller
	 */
	public SendPasswordResetCodeView(Controller controller) {
		super(controller, "Reset Password", BUTTON_NAMES);
		subscribeToController(Key.MESSAGE, "BannerID");
	}

	@Override
	protected void build() {
		form = new BannerIdForm(this);
		add(form);
	}
	
	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Cancel")) {
			controller.stateChangeRequest(Key.DISPLAY_LOGIN, null);
		} else if (source == buttons.get("Submit")) {
			form.setAllFieldsEnabled(false);
			buttons.get("Submit").setEnabled(false);
			controller.stateChangeRequest(Key.SEND_RESET_CODE, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		form.setAllFieldsEnabled(true);
		buttons.get("Submit").setEnabled(true);
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}else if(key.equals("BannerID")){
			form.get("BannerID").setValue((String) value);
		}
	}
	
	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}
}
