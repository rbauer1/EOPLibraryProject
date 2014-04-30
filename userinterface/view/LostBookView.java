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
import model.Borrower;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import userinterface.view.form.Form;
import userinterface.view.form.RentalForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to confirm a lost book
 */
public class LostBookView extends View {

	private static final long serialVersionUID = -3913733571425788842L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Confirm", "Cancel"};

	/** Form to take in data */
	private Form form;

	/**
	 * Constructs delete book view
	 * @param controller
	 */
	public LostBookView(Controller controller) {
		super(controller, "Confirm Lost Book", BUTTON_NAMES);
		subscribeToController(Key.BOOK, Key.BORROWER, Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		form.get("SuggestedPrice").requestFocusInWindow();
	}

	@Override
	protected void build() {
		messagePanel.displayMessage(MessageType.WARNING, "Caution! Please verify you have selected the correct book to be marked as lost.");
		form = new RentalForm(this);
		form.setAllFieldsEnabled(false);
		form.setFieldEnabled("Notes", true);
		form.setFieldEnabled("SuggestedPrice", true);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Cancel")) {
			controller.stateChangeRequest(Key.BACK, "ListRentalsView");
		}else if (source == buttons.get("Confirm") || source == form) {
			controller.stateChangeRequest(Key.SAVE_BOOK, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if(key.equals(Key.BOOK)){
			Book book = (Book) value;
			form.updateValues(book.getPersistentState());
			form.get("Notes").reset();
		}else if(key.equals(Key.BORROWER)){
			Borrower borrower = (Borrower) value;
			form.updateValues(borrower.getPersistentState());
			form.get("Notes").reset();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}