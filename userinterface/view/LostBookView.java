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
import model.Borrower;
import model.Model;
import userinterface.message.MessageType;
import userinterface.view.form.Form;
import userinterface.view.form.RentalForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to delete book.
 */
public class LostBookView extends View {
	private static final long serialVersionUID = -3913733571425788842L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Confirm", "Cancel"};

	/** Form to take in data */
	private Form form;
	
	/** Model whose data we are displaying */
	private Model book;
	
	/** Model whose data we are displaying */
	private Model borrower;
	
	/**
	 * Constructs delete book view
	 * @param controller
	 */
	public LostBookView(Controller controller) {
		super(controller, "Confirm Lost Book", BUTTON_NAMES);
		subscribeToController(Key.BOOK, Key.BORROWER, Key.INPUT_ERROR, Key.SAVE_SUCCESS, Key.SAVE_ERROR);
	}
	
	@Override
	protected void build() {
		messagePanel.displayMessage(MessageType.WARNING, "Caution! Please verify you have selected the correct book to be marked as lost.");
		form = new RentalForm(this);
		form.setFieldEnabled("Barcode", false);
		form.setFieldEnabled("Title", false);
		form.setFieldEnabled("Author1", false);
		form.setFieldEnabled("BannerID", false);
		form.setFieldEnabled("FirstName", false);
		form.setFieldEnabled("LastName", false);
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

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {	
		if(key.equals(Key.BOOK)){
			book = (Book) value;
			form.setValues(book.getPersistentState());
			form.get("Notes").reset();
		}else if(key.equals(Key.BORROWER)){
			borrower = (Borrower) value;
			form.setValues(borrower.getPersistentState());
			form.get("Notes").reset();
		}else if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage("Aw shucks! There are errors in the input. Please try again.", (List<String>) value);
		}else if(key.equals(Key.SAVE_SUCCESS)){
			messagePanel.displayMessage(MessageType.SUCCESS, "Well done! Book was sucessfully deleted.");
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