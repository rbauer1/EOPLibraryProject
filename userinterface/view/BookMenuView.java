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
		add(createMenuButtons());
	}

	/**
	 * Creates the menu buttons
	 * @return buttonPanel
	 */
	private JPanel createMenuButtons() {
		JPanel buttonPanel = new Panel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		addButton = new Button("Add Book");
		addButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(addButton));

		modifyButton = new Button("Modify Book");
		modifyButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(modifyButton));

		deleteButton = new Button("Delete Book");
		deleteButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(deleteButton));

		processLostBookButton = new Button("Process Lost Book");
		processLostBookButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(processLostBookButton));

		listAvailableButton = new Button("List Available Books");
		listAvailableButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(listAvailableButton));

		listUnavailableButton = new Button("List Unavailable Books");
		listUnavailableButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(listUnavailableButton));

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
			controller.stateChangeRequest(Key.EXECUTE_ADD_BOOK, null);
		}else if (evt.getSource() == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_BOOK, null);
		} else if (evt.getSource() == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_BOOK, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
