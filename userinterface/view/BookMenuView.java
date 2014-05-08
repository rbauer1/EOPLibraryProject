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

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.Button;
import utilities.Key;
import controller.Controller;

/**
 * Book Menu Screen. Serves as the transaction selection screen for book actions.
 */
public class BookMenuView extends View {

	private static final long serialVersionUID = -4462137345508528750L;

	/* Buttons */
	private Button addButton;
	private Button modifyButton;
	private Button deleteButton;
	private Button processLostBookButton;
	private Button listAvailableButton;
	private Button listUnavailableButton;
	private Button backButton;

	/**
	 * Constructs book menu view
	 * @param controller
	 */
	public BookMenuView(Controller controller) {
		super(controller, "Book Menu");
	}

	@Override
	protected void build() {
		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
            addButton = new Button("Add Book", this);
            add(ViewHelper.formatCenter(addButton));

            modifyButton = new Button("Modify Book", this);
            add(ViewHelper.formatCenter(modifyButton));

            deleteButton = new Button("Delete Book", this);
            add(ViewHelper.formatCenter(deleteButton));

            processLostBookButton = new Button("Process Lost Book", this);
            add(ViewHelper.formatCenter(processLostBookButton));

		}

		listAvailableButton = new Button("List Available Books", this);
		add(ViewHelper.formatCenter(listAvailableButton));

		listUnavailableButton = new Button("List Unavailable Books", this);
		add(ViewHelper.formatCenter(listUnavailableButton));

		backButton = new Button("Back", this);
		add(ViewHelper.formatCenter(backButton));
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
