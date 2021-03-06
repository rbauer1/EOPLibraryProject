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
import userinterface.message.MessageType;
import userinterface.view.form.Form;
import userinterface.view.form.WorkerForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to add new worker.
 */
public class AddWorkerView extends View {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Add", "Reset", "Back"};

	private static final long serialVersionUID = -6030753682831962753L;

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs add worker view
	 * @param controller
	 */
	public AddWorkerView(Controller controller) {
		super(controller, "Add Worker", BUTTON_NAMES);
		subscribeToController(Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}

	@Override
	protected void build() {
		form = new WorkerForm(this);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		}else if (source == buttons.get("Reset")){
			form.reset();
			form.setAllFieldsEnabled(true);
			buttons.get("Add").setEnabled(true);
		}else if (source == buttons.get("Add") || source == form) {
			form.setAllFieldsEnabled(false);
			buttons.get("Add").setEnabled(false);
			controller.stateChangeRequest(Key.SAVE_WORKER, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.MESSAGE)) {
			MessageEvent event = (MessageEvent)value;
			if(event.getType() == MessageType.ERROR){
				form.setAllFieldsEnabled(true);
				buttons.get("Add").setEnabled(true);
			}
			messagePanel.displayMessage(event);
		}
	}
}