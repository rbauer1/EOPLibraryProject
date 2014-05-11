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

import javax.swing.JPanel;

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.Button;
import userinterface.component.flat.FButton;
import userinterface.utilities.Utils;
import utilities.Key;
import controller.Controller;

/**
 * Book Menu Screen. Serves as the transaction selection screen for book actions.
 */
public class BookMenuView extends MenuView {

	private static final long serialVersionUID = -4462137345508528750L;

	/* Buttons */
	private FButton processLostBookButton;
	private FButton listAvailableButton;
	private FButton listUnavailableButton;
	private FButton backButton;

	/**
	 * Constructs book menu view
	 * @param controller
	 */
	public BookMenuView(Controller controller) {
		super(controller, "Book Menu");
	}
	
	@Override
	public void beforeShown() {
		super.beforeShown();
		
		processLostBookButton.reset();
		listAvailableButton.reset();
		listUnavailableButton.reset();
		backButton.reset();
	}
	
	@Override
	protected void build() {
		super.build();

		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
            processLostBookButton = createButton("Process Lost Book");
            body.add(processLostBookButton);
            body.add(createButtonSeparator());
		}

		listAvailableButton = createButton("List Available Books");
		body.add(listAvailableButton);
		body.add(createButtonSeparator());
		
		listUnavailableButton = createButton("List Unavailable Books");
		body.add(listUnavailableButton);
		body.add(createButtonSeparator());

		backButton = createButton("Back");
		body.add(backButton);
	}

	public Accordion toMenu() {
		build(); // FIXME could be avoided ? Should the general view be removed ? Or replaced by that menu ?

		Accordion menu = new Accordion();

		menu.add(addButton);
		menu.add(modifyButton);
		menu.add(deleteButton);
		menu.add(processLostBookButton);
		menu.add(listUnavailableButton);
		menu.add(listAvailableButton);

		return menu;
	}

	@Override
	protected String getMenuName() {
		return "Book";
	}

	@Override
	public void processAction(Object source) {
		if (source == backButton) {
			controller.stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		} else if (source == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_BOOK, null);
		}else if (source == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_BOOK, null);
		} else if (source == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_BOOK, null);
		} else if (source == processLostBookButton) {
			controller.stateChangeRequest(Key.EXECUTE_PROCESS_LOST_BOOK, null);
		} else if (source == listUnavailableButton) {
			controller.stateChangeRequest(Key.EXECUTE_LIST_RENTED_BOOKS, null);
		} else if (source == listAvailableButton) {
			controller.stateChangeRequest(Key.EXECUTE_LIST_AVAILABLE_BOOKS, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
