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
import userinterface.component.Accordion;
import userinterface.component.Button;
import userinterface.component.flat.FButton;
import utilities.Key;
import controller.Controller;

/**
 * Worker Menu Screen. Serves as the transaction selection screen for Worker actions.
 */
public class BorrowerMenuView extends MenuView {

	private static final long serialVersionUID = -7493657951613059489L;

	/* Buttons */
	private FButton backButton;
	private FButton listBorrowersButton;
	private FButton delinquencyButton;

	/**
	 * Constructs Worker menu view
	 * @param controller
	 */
	public BorrowerMenuView(Controller controller) {
		super(controller, "Borrower Menu");
	}

	@Override
	protected String getMenuName() {
		return "Borrower";
	}

	@Override
	protected void build() {
		super.build();

		listBorrowersButton = createButton("List Borrowers With Rented Books");
		body.add(listBorrowersButton);
		body.add(createButtonSeparator());

		delinquencyButton = createButton("Delinquency Check");
		body.add(delinquencyButton);
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

		return menu;
	}

	@Override
	public void processAction(Object source) {
		if (source == backButton) {
			controller.stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		} else if (source == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_BORROWER, null);
		}else if (source == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_BORROWER, null);
		} else if (source == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_BORROWER, null);
		} else if (source == listBorrowersButton) {
			controller.stateChangeRequest(Key.EXECUTE_LIST_BORROWERS_WITH_RENTED_BOOKS, null);
		} else if (source == delinquencyButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELINQUENCY_CHECK, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
