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
 * Worker Menu Screen. Serves as the transaction selection screen for Worker actions.
 */
public class BorrowerMenuView extends View {
	
	private static final long serialVersionUID = -7493657951613059489L;
	
	/* Buttons */
	private Button addButton;
	private Button modifyButton;
	private Button deleteButton;
	private Button backButton;

	/**
	 * Constructs Worker menu view
	 * @param controller
	 */
	public BorrowerMenuView(Controller controller) {
		super(controller, "Choose a Transaction Operation");
	}

	@Override
	protected void build() {
		addButton = new Button("Add Borrower", this);
		add(ViewHelper.formatCenter(addButton));

		modifyButton = new Button("Modify Borrower", this);
		add(ViewHelper.formatCenter(modifyButton));

		deleteButton = new Button("Delete Borrower", this);
		add(ViewHelper.formatCenter(deleteButton));

		backButton = new Button("Back", this);
		add(ViewHelper.formatCenter(backButton));
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
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
