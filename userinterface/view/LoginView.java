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
import userinterface.screen.LoginScreen;
import userinterface.view.form.Form;
import userinterface.view.form.LoginForm;
import utilities.Key;
import controller.Controller;

/**
 * The Login View for the EOP Library application.
 * Provides the interface for the workers to login to the system.
 */
public class LoginView extends View {

	private static final long serialVersionUID = 5785171554484853130L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Login", "Forgot Password"};

	/** Form to take in worker login data */
	private Form form;

	/**
	 * Constructs Login view object and subscribes to the Librarian model.
	 * @param model The Librarian
	 */
	public LoginView(Controller controller) {
		super(controller, "Login", BUTTON_NAMES);
		subscribeToController(Key.MESSAGE);
	}

	public String getScreenName() {
		return LoginScreen.class.getSimpleName();
	}

	@Override
	protected void build() {
		form = new LoginForm(this);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Forgot Password")) {
			controller.stateChangeRequest(Key.EXECUTE_RECOVER_PASSWORD, form.get("BannerID").getValue());
		} else if (source == buttons.get("Login") || source == form) {
			form.setAllFieldsEnabled(false);
			buttons.get("Login").setEnabled(false);
			controller.stateChangeRequest(Key.LOGIN, form.getValues());
		}
		form.get("Password").reset();
	}

	@Override
	public void updateState(String key, Object value) {
		form.requestFocusForDefaultField();
		form.setAllFieldsEnabled(true);
		buttons.get("Login").setEnabled(true);
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}

	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
		form.reset();
		messagePanel.clear();
	}
}
