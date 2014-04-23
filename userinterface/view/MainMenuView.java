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

import userinterface.MenuPanel;
import userinterface.MainFrame;
import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.menu.Menu;
import userinterface.menu.MenuFactory;
import userinterface.message.MessageEvent;
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
	private Button checkinBookButton;
	private Button checkoutBookButton;
	private Button logoutButton;
	private Button workerActionsButton;

	/**
	 * Constructs main menu view
	 * @param controller
	 */
	public MainMenuView(Controller controller) {
		super(controller, "Main Menu");
		subscribeToController(Key.MESSAGE);

		// menu.add("Check in a book", /* TODO */ );
		// menu.add("Check out a book", /* TODO */ );
	}


	@Override
	public void afterShown(){
		messagePanel.clear();
	}

	@Override
	protected void build() {
		bookActionsButton = new Button("Book Menu", this);
		add(ViewHelper.formatCenter(bookActionsButton));

		if(((Worker)controller.getState(Key.WORKER)).isAdmin()){
			borrowerActionsButton = new Button("Borrower Menu", this);
			add(ViewHelper.formatCenter(borrowerActionsButton));

			workerActionsButton = new Button("Workers Menu", this);
			add(ViewHelper.formatCenter(workerActionsButton));
		}


		checkoutBookButton = new Button("Rent a Book", this);
		add(ViewHelper.formatCenter(checkoutBookButton));

		checkinBookButton = new Button("Return a Book", this);
		add(ViewHelper.formatCenter(checkinBookButton));

		logoutButton = new Button("Logout", this);
		add(ViewHelper.formatCenter(logoutButton));

		MenuPanel menu = MainFrame.getInstance().getMenu();
		
		Menu bookMenu = MenuFactory.createMenu("BookMenu", controller);
		Menu borrowerMenu = MenuFactory.createMenu("BorrowerMenu", controller);
		Menu workerMenu = MenuFactory.createMenu("WorkerMenu", controller);

		menu.add(bookMenu.getParent(), bookMenu.getContent());
		menu.add(borrowerMenu.getParent(), borrowerMenu.getContent());
		menu.add(workerMenu.getParent(), workerMenu.getContent());
		menu.add(checkinBookButton);
		menu.add(checkoutBookButton);
		menu.add(logoutButton);
	}

	@Override
	public void processAction(Object source) {
		if (source == logoutButton) {
			controller.stateChangeRequest(Key.LOGOUT, null);

			// Clear the current content the side menu
			MainFrame.getInstance().getMenu().clear();
		} else if (source == bookActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		} else if (source == borrowerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		} else if (source == workerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		} else if (source == checkoutBookButton) {
			controller.stateChangeRequest(Key.EXECUTE_CHECKOUT_BOOK, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}

}
