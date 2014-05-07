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

import model.Book;
import userinterface.message.MessageEvent;
import userinterface.view.form.BookForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to modify book.
 */
public class ModifyBookView extends View {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Recover", "Save", "Reset", "Back"};

	private static final long serialVersionUID = 3340451129170570186L;

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs modify book view
	 * @param controller
	 */
	public ModifyBookView(Controller controller) {
		super(controller, "Modify Book", BUTTON_NAMES);
		subscribeToController(Key.BOOK, Key.MESSAGE);
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
			controller.stateChangeRequest(Key.BACK, "ListBooksView");
		}else if (source == buttons.get("Reset")){
			form.setAllFieldsEnabled(false);
			buttons.get("Save").setEnabled(false);
			buttons.get("Reset").setEnabled(false);
			controller.stateChangeRequest(Key.RELOAD_ENTITY, null);
		}else if (source == buttons.get("Save") || source == form) {
			form.setAllFieldsEnabled(false);
			buttons.get("Save").setEnabled(false);
			buttons.get("Reset").setEnabled(false);
			controller.stateChangeRequest(Key.SAVE_BOOK, form.getValues());
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
		form.setFieldEnabled(Book.PRIMARY_KEY, false);
		buttons.get("Recover").getParent().setVisible(!active);
		buttons.get("Save").getParent().setVisible(active);
		buttons.get("Save").setEnabled(true);
		buttons.get("Reset").setEnabled(true);
	}

	@Override
	public void updateState(String key, Object value) {
		setFormActive(true);
		if(key.equals(Key.BOOK)){
			Book book = (Book) value;
			form.setValues(book.getPersistentState());
			setFormActive(book.isActive());
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}