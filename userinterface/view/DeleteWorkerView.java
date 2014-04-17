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

import model.Model;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import userinterface.view.form.Form;
import userinterface.view.form.WorkerDeleteForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to delete worker.
 */
public class DeleteWorkerView extends View {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Delete", "Back"};

	private static final long serialVersionUID = -6030753682831962753L;

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs delete worker view
	 * @param controller
	 */
	public DeleteWorkerView(Controller controller) {
		super(controller, "Delete Worker", BUTTON_NAMES);
		subscribeToController(Key.WORKER, Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}

	@Override
	protected void build() {
		messagePanel.displayMessage(MessageType.WARNING, "Caution! Please verify you have selected the correct worker for deletion.");
		form = new WorkerDeleteForm(this);
		form.setFieldEnabled("BannerID", false);
		form.setFieldEnabled("FirstName", false);
		form.setFieldEnabled("LastName", false);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListWorkersView");
		}else if (source == buttons.get("Delete") || source == form) {
			controller.stateChangeRequest(Key.SAVE_WORKER, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if(key.equals(Key.WORKER)){
			form.setValues(((Model) value).getPersistentState());
			form.get("Notes").reset();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}