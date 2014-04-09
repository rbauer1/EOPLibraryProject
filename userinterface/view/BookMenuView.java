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

import userinterface.ViewHelper;
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
		super(controller, "Choose a Transaction Operation");
	}

	@Override
	protected void build() {
		addButton = new Button("Add Book");
		addButton.addActionListener(this);
		add(ViewHelper.formatCenter(addButton));

		modifyButton = new Button("Modify Book");
		modifyButton.addActionListener(this);
		add(ViewHelper.formatCenter(modifyButton));

		deleteButton = new Button("Delete Book");
		deleteButton.addActionListener(this);
		add(ViewHelper.formatCenter(deleteButton));

		processLostBookButton = new Button("Process Lost Book");
		processLostBookButton.addActionListener(this);
		add(ViewHelper.formatCenter(processLostBookButton));

		listAvailableButton = new Button("List Available Books");
		listAvailableButton.addActionListener(this);
		add(ViewHelper.formatCenter(listAvailableButton));

		listUnavailableButton = new Button("List Unavailable Books");
		listUnavailableButton.addActionListener(this);
		add(ViewHelper.formatCenter(listUnavailableButton));

		backButton = new Button("Back");
		backButton.addActionListener(this);
		add(ViewHelper.formatCenter(backButton));
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
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
