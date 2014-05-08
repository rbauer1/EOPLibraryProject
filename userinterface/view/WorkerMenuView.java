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
import utilities.Key;
import controller.Controller;

/**
 * Worker Menu Screen. Serves as the transaction selection screen for Worker actions.
 */
public class WorkerMenuView extends MenuView {
	
	private static final long serialVersionUID = -4462137345508528750L;
	
	/* Buttons */
	private Button backButton;

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

		backButton = new Button("Back", this);
		add(ViewHelper.formatCenter(backButton));
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
