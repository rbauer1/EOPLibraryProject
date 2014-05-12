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
import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import userinterface.view.form.Form;
import userinterface.view.form.WorkerForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to modify worker.
 */
public class ModifyWorkerView extends View {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Recover", "Save", "Reset", "Back"};

	private static final long serialVersionUID = 3340451129170570186L;

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs modify worker view
	 * @param controller
	 */
	public ModifyWorkerView(Controller controller) {
		super(controller, "Modify Worker", BUTTON_NAMES);
		subscribeToController(Key.WORKER, Key.MESSAGE);
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
			controller.stateChangeRequest(Key.BACK, "ListWorkersView");
		}else if (source == buttons.get("Reset")){
			form.setAllFieldsEnabled(false);
			buttons.get("Save").setEnabled(false);
			buttons.get("Reset").setEnabled(false);
			controller.stateChangeRequest(Key.RELOAD_ENTITY, null);
		}else if (source == buttons.get("Save") || source == form) {
			form.setAllFieldsEnabled(false);
			buttons.get("Save").setEnabled(false);
			buttons.get("Reset").setEnabled(false);
			controller.stateChangeRequest(Key.SAVE_WORKER, form.getValues());
		}else if (source == buttons.get("Recover")) {
			setFormActive(true);
		}
	}

	/**
	 * Sets up the modify form depending on if the entity is active
	 * @param active
	 */
	private void setFormActive(boolean active){
		form.setAllFieldsEnabled(active);
		form.setFieldEnabled(Worker.PRIMARY_KEY, false);
		form.setFieldEnabled(WorkerForm.PASSORD_FIELD, false);
		form.setFieldEnabled(WorkerForm.CONFIRM_PASSORD_FIELD, false);
		buttons.get("Recover").getParent().setVisible(!active);
		buttons.get("Save").getParent().setVisible(active);
		buttons.get("Save").setEnabled(true);
		buttons.get("Reset").setEnabled(true);
		if(!active){
			messagePanel.displayMessage(MessageType.INFO, "Heads Up! This worker is archived. It must be recovered before it can be modified.");
		}
	}

	@Override
	public void updateState(String key, Object value) {
		setFormActive(true);
		if(key.equals(Key.WORKER)){
			Model worker = (Model) value;
			form.setValues(worker.getPersistentState());
			setFormActive(!worker.getState("Status").equals("Inactive"));
			//If worker being modified is same as worker logged in, password may be changed 
			if (((Worker)getController().getState(Key.LOGGED_IN_WORKER)).getPrimaryKeyValue().equals(worker.getPrimaryKeyValue())){
				form.setFieldEnabled(WorkerForm.PASSORD_FIELD, true);
				form.setFieldEnabled(WorkerForm.CONFIRM_PASSORD_FIELD, true);
			}
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}