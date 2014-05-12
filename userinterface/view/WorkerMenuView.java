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

import userinterface.component.flat.FButton;
import utilities.Key;
import controller.Controller;

/**
 * Worker Menu Screen. Serves as the transaction selection screen for Worker actions.
 */
public class WorkerMenuView extends MenuView {
	
	private static final long serialVersionUID = -4462137345508528750L;
	
	/* Buttons */
	private FButton backButton;

	/**
	 * Constructs Worker menu view
	 * @param controller
	 */
	public WorkerMenuView(Controller controller) {
		super(controller, "Worker Menu");
	}
	
	@Override
	protected String getMenuName() {
		return "Worker";
	}

	@Override
	protected void build() {
		super.build();

		backButton = createBackButton("Back");
		body.add(backButton);
	}
	
	@Override
	public void processAction(Object source) {
		if (source == backButton) {
			controller.stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		} else if (source == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_WORKER, null);
		}else if (source == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_WORKER, null);
		} else if (source == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_WORKER, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
