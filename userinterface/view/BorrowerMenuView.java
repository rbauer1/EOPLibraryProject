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

import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import utilities.Key;
import controller.Controller;

/**
 * Worker Menu Screen. Serves as the transaction selection screen for Worker actions.
 */
public class BorrowerMenuView extends View {
	
	private static final long serialVersionUID = -4462137345508528750L;
	
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
		add(createMenuButtons());
	}

	/**
	 * Creates the menu buttons
	 * @return buttonPanel
	 */
	private JPanel createMenuButtons() {
		JPanel buttonPanel = new Panel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		addButton = new Button("Add Worker");
		addButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(addButton));

		modifyButton = new Button("Modify Worker");
		modifyButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(modifyButton));

		deleteButton = new Button("Delete Worker");
		deleteButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(deleteButton));

		backButton = new Button("Back");
		backButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(backButton));

		return buttonPanel;
	}

	@Override
	public void processAction(EventObject evt) {
		if (evt.getSource() == backButton) {
			controller.stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		} else if (evt.getSource() == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_WORKER, null);
		}else if (evt.getSource() == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_WORKER, null);
		} else if (evt.getSource() == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_WORKER, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
