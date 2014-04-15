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
		super(controller, "Main Menu");
	}


	@Override
	protected void build() {
		bookActionsButton = new Button("Book Menu");
		bookActionsButton.addActionListener(this);
		add(ViewHelper.formatCenter(bookActionsButton));

		borrowerActionsButton = new Button("Borrower Menu");
		borrowerActionsButton.addActionListener(this);
		add(ViewHelper.formatCenter(borrowerActionsButton));

		workerActionsButton = new Button("Workers Menu");
		workerActionsButton.addActionListener(this);
		add(ViewHelper.formatCenter(workerActionsButton));

		checkoutBookButton = new Button("Rent a Book");
		checkoutBookButton.addActionListener(this);
		add(ViewHelper.formatCenter(checkoutBookButton));
		
		checkinBookButton = new Button("Return a Book");
		checkinBookButton.addActionListener(this);
		add(ViewHelper.formatCenter(checkinBookButton));

		logoutButton = new Button("Logout");
		logoutButton.addActionListener(this);
		add(ViewHelper.formatCenter(logoutButton));
	}

	@Override
	public void processAction(Object source) {
		if (source == logoutButton) {
			controller.stateChangeRequest(Key.LOGOUT, null);
		} else if (source == bookActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		} else if (source == borrowerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		} else if (source == workerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {	}
	
}
