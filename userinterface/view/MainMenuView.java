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
 * Main Menu Screen. Serves as the main transaction selection screen.
 */
public class MainMenuView extends View {
	
	private static final long serialVersionUID = -4462137345508528750L;
	
	/* Buttons */
	private Button bookActionsButton;
	private Button borrowerActionsButton;
	private Button workerActionsButton;
	private Button checkinBookButton;
	private Button checkoutBookButton;
	private Button logoutButton;

	/**
	 * Constructs main menu view
	 * @param controller
	 */
	public MainMenuView(Controller controller) {
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

		bookActionsButton = new Button("Book Actions");
		bookActionsButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(bookActionsButton));

		borrowerActionsButton = new Button("Borrower Actions");
		borrowerActionsButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(borrowerActionsButton));

		workerActionsButton = new Button("Workers Actions");
		workerActionsButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(workerActionsButton));

		checkinBookButton = new Button("Check in a Book");
		checkinBookButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(checkinBookButton));

		checkoutBookButton = new Button("Check out a Book");
		checkoutBookButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(checkoutBookButton));

		logoutButton = new Button("Logout");
		logoutButton.addActionListener(this);
		buttonPanel.add(ViewHelper.formatCenter(logoutButton));

		return buttonPanel;
	}

	@Override
	public void processAction(EventObject evt) {
		if (evt.getSource() == logoutButton) {
			controller.stateChangeRequest(Key.LOGOUT, null);
		} else if (evt.getSource() == bookActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		} else if (evt.getSource() == borrowerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		} else if (evt.getSource() == workerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {}
}
