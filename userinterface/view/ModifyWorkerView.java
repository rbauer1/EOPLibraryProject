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

import java.util.List;

import model.Worker;
import model.Model;
import userinterface.view.form.WorkerForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to modify worker.
 */
public class ModifyWorkerView extends View {
	
	private static final long serialVersionUID = 3340451129170570186L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Recover", "Save", "Reset", "Back"};

	/** Form to take in data */
	private Form form;
	
	/** Model whose data we are displaying */
	private Model worker;
	
	/**
	 * Constructs modify worker view
	 * @param controller
	 */
	public ModifyWorkerView(Controller controller) {
		super(controller, "Modify Worker", BUTTON_NAMES);
		subscribeToController(Key.WORKER, Key.INPUT_ERROR, Key.SAVE_SUCCESS, Key.SAVE_ERROR);
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
			controller.stateChangeRequest(Key.RELOAD_ENTITY, null);
		}else if (source == buttons.get("Save") || source == form) {
			controller.stateChangeRequest(Key.SAVE_WORKER, form.getValues());
		}else if (source == buttons.get("Recover")) {
			setFormActive(true);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if(key.equals(Key.WORKER)){
			worker = (Worker) value;
			form.setValues(worker.getPersistentState());
			setFormActive(!worker.getState("Status").equals("Inactive"));
		}else if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage("Aw shucks! There are errors in the input. Please try again.", (List<String>) value);
		}else if(key.equals(Key.SAVE_SUCCESS)){
			messagePanel.displayMessage("Success", "Well done! Worker was sucessfully saved."); 
		}else if(key.equals(Key.SAVE_ERROR)){
			messagePanel.displayErrorMessage("Whoops! An error occurred while saving.");
		}
	}
	
	/**
	 * Sets up the modify form depending on if the entity is active
	 * @param active
	 */
	private void setFormActive(boolean active){
		form.setAllFieldsEnabled(active);
		form.setFieldEnabled(worker.getPrimaryKey(), false);
		buttons.get("Recover").getParent().setVisible(!active);
		buttons.get("Save").getParent().setVisible(active);
		if(!active){
			messagePanel.displayMessage("Info", "Heads Up! This worker is archived. It must be recovered before it can be modified.");
		}
	}
}