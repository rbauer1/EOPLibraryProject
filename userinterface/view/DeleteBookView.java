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
import userinterface.view.form.BookDeleteForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to delete book.
 */
public class DeleteBookView extends View {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Delete", "Back"};

	private static final long serialVersionUID = -6030753682831962753L;

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs delete book view
	 * @param controller
	 */
	public DeleteBookView(Controller controller) {
		super(controller, "Delete Book", BUTTON_NAMES);
		subscribeToController(Key.BOOK, Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}

	@Override
	protected void build() {
		messagePanel.displayMessage(MessageType.WARNING, "Caution! Please verify you have selected the correct book for deletion.");
		form = new BookDeleteForm(this);
		form.setAllFieldsEnabled(false);
		form.setFieldEnabled("DeletionReason", true);
		form.setFieldEnabled("Notes", true);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListBooksView");
		}else if (source == buttons.get("Delete") || source == form) {
			form.setAllFieldsEnabled(false);
			buttons.get("Delete").setEnabled(false);
			controller.stateChangeRequest(Key.SAVE_BOOK, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		form.setFieldEnabled("DeletionReason", true);
		form.setFieldEnabled("Notes", true);
		buttons.get("Delete").setEnabled(true);
		if(key.equals(Key.BOOK)){
			form.setValues(((Model) value).getPersistentState());
			form.get("Notes").reset();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}