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

import model.Book;
import model.Model;
import userinterface.view.form.BookForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to modify book.
 */
public class ModifyBookView extends View {
	
	private static final long serialVersionUID = 3340451129170570186L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Recover", "Save", "Reset", "Back"};

	/** Form to take in data */
	private Form form;
	
	/** Model whose data we are displaying */
	private Model book;
	
	/**
	 * Constructs modify book view
	 * @param controller
	 */
	public ModifyBookView(Controller controller) {
		super(controller, "Modify Book", BUTTON_NAMES);
		subscribeToController(Key.BOOK, Key.INPUT_ERROR, Key.SAVE_SUCCESS, Key.SAVE_ERROR);
	}
	
	@Override
	protected void build() {
		form = new BookForm(this);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		}else if (source == buttons.get("Reset")){
			controller.stateChangeRequest(Key.RELOAD_ENTITY, null);
		}else if (source == buttons.get("Save") || source == form) {
			controller.stateChangeRequest(Key.SAVE_BOOK, form.getValues());
		}else if (source == buttons.get("Recover")) {
			setFormActive(true);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if(key.equals(Key.BOOK)){
			book = (Book) value;
			form.setValues(book.getPersistentState());
			setFormActive(!book.getState("Status").equals("Inactive"));
		}else if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage("Aw shucks! There are errors in the input. Please try again.", (List<String>) value);
		}else if(key.equals(Key.SAVE_SUCCESS)){
			messagePanel.displayMessage("Success", "Well done! Book was sucessfully saved."); 
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
		form.setFieldEnabled(book.getPrimaryKey(), false);
		buttons.get("Recover").getParent().setVisible(!active);
		buttons.get("Save").getParent().setVisible(active);
		if(!active){
			messagePanel.displayMessage("Info", "Heads Up! This book is archived. It must be recovered before it can be modified.");
		}
	}
}