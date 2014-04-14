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

import userinterface.view.form.BookDeleteForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to delete book.
 */
public class DeleteBookView extends View {
	
	private static final long serialVersionUID = -6030753682831962753L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Delete", "Back"};

	/** Form to take in data */
	private Form form;
	
	/** Model whose data we are displaying */
	private Model book;
	
	/**
	 * Constructs delete book view
	 * @param controller
	 */
	public DeleteBookView(Controller controller) {
		super(controller, "Delete Book", BUTTON_NAMES);
		subscribeToController(Key.BOOK, Key.INPUT_ERROR, Key.SAVE_SUCCESS, Key.SAVE_ERROR);
	}
	
	@Override
	protected void build() {
		messagePanel.displayMessage("Warning", "Caution! Please verify you have selected the correct book for deletion.");
		form = new BookDeleteForm(this);
		form.setFieldEnabled("Barcode", false);
		form.setFieldEnabled("Title", false);
		form.setFieldEnabled("Author1", false);
		add(form);		
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		}else if (source == buttons.get("Delete") || source == form) {
			controller.stateChangeRequest(Key.SAVE_BOOK, form.getValues());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {	
		if(key.equals(Key.BOOK)){
			book = (Book) value;
			form.setValues(book.getPersistentState());
		}else if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage("Aw shucks! There are errors in the input. Please try again.", (List<String>) value);
		}else if(key.equals(Key.SAVE_SUCCESS)){
			messagePanel.displayMessage("Success", "Well done! Book was sucessfully deleted.");
			form.reset();
		}else if(key.equals(Key.SAVE_ERROR)){
			messagePanel.displayErrorMessage("Whoops! An error occurred while deleting.");
		}
	}
	
	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}
}