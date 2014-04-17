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
import userinterface.view.form.BorrowerForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to add new borrower.
 */
public class AddBorrowerView extends View {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Add", "Reset", "Back"};

	private static final long serialVersionUID = -6030753682831962753L;

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs add borrower view
	 * @param controller
	 */
	public AddBorrowerView(Controller controller) {
		super(controller, "Add Borrower", BUTTON_NAMES);
		subscribeToController(Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}

	@Override
	protected void build() {
		form = new BorrowerForm(this);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		}else if (source == buttons.get("Reset")){
			form.reset();
		}else if (source == buttons.get("Add") || source == form) {
			controller.stateChangeRequest(Key.SAVE_BORROWER, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}