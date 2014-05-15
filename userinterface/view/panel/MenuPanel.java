package userinterface.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import model.Worker;
import userinterface.MainFrame;
import userinterface.component.Accordion;
import userinterface.component.Button;
import userinterface.component.flat.FButton;
import userinterface.component.flat.Icons;
import userinterface.menu.MButton;
import userinterface.utilities.ButtonSet;
import userinterface.utilities.Utils;
import userinterface.view.View;
import utilities.Key;
import controller.Controller;

public class MenuPanel extends View {

	private static final long serialVersionUID = -6319800980044183733L;

	/** Background color of the header */
	private static final Color BACKGROUND_COLOR = new Color(0xD1DBC6);

	public static final int WIDTH = (int)(MainFrame.WIDTH / 4.5);
	public static final int HEIGHT = MainFrame.HEIGHT;

	private static final ButtonSet menuSet = new ButtonSet();
	private Accordion accordion; //TODO is this relevant anymore?

	/* Buttons */
	private FButton bookActionsButton;
	private FButton borrowerActionsButton;
	private FButton rentBookButton;
	private FButton returnBookButton;
	private FButton workerActionsButton;
	private FButton settingsButton;

	public MenuPanel(Controller controller) {
		super(controller);
	}

	public void add(JComponent comp) {
		if (comp instanceof FButton) {
			FButton button = (FButton)comp;
			button.attachSet(menuSet);
		}
		System.out.println(accordion.toString());
		accordion.add(comp);
	}

	public void add(MButton but, JComponent container) {
		accordion.add(but, container);
	}

	public void add(String but, JComponent[] subs) {
		Button b = new Button(but);
		Accordion container = new Accordion();

		for (JComponent sub : subs) {
			container.add(sub);
		}
		accordion.add(b, container);
	}

	@Override
	protected void build() {
		accordion = new Accordion();

		setLayout(new BorderLayout(0,5));
		setBackground(BACKGROUND_COLOR);
		Utils.setAllSize(this, WIDTH, HEIGHT);

		accordion.setBackground(BACKGROUND_COLOR);
		add(accordion, BorderLayout.PAGE_START);

		bookActionsButton = new MButton("Book Actions", Icons.BOOK, this);
		add(bookActionsButton);
		add(createSeparator());

		if (((Worker)controller.getState(Key.LOGGED_IN_WORKER)).isAdmin()){
			borrowerActionsButton = new MButton("Borrower Actions", Icons.BORROWER, this);
			add(borrowerActionsButton);
			add(createSeparator());

			workerActionsButton = new MButton("Workers Actions", Icons.WORKER, this);
			add(workerActionsButton);
			add(createSeparator());
		}

		rentBookButton = new MButton("Rent a Book", Icons.RENT_BOOK, this);
		add(rentBookButton);
		add(createSeparator());

		returnBookButton = new MButton("Return a Book", Icons.RETURN_BOOK, this);
		add(returnBookButton);
		add(createSeparator());
		if (((Worker)controller.getState(Key.LOGGED_IN_WORKER)).isAdmin()){
			settingsButton = new MButton("Settings", Icons.GEAR, this);
			add(settingsButton);
		}
	}

	private JPanel createSeparator() {
		JPanel sep = new JPanel();
		Utils.setAllSize(sep, WIDTH, 2);
		Utils.setTransparent(sep);

		return sep;
	}

	@Override
	public void processAction(Object source) {
		if (source == bookActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		} else if (source == borrowerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		} else if (source == workerActionsButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		} else if (source == rentBookButton) {
			controller.stateChangeRequest(Key.EXECUTE_RENT_BOOK, null);
		} else if (source == returnBookButton) {
			controller.stateChangeRequest(Key.EXECUTE_RETURN_BOOK, null);
		} else if (source == settingsButton) {
			controller.stateChangeRequest(Key.EXECUTE_CHANGE_MAX_DUE_DATE, null);
		}
	}

	public void reset() {
		bookActionsButton.reset();
		if (((Worker)controller.getState(Key.LOGGED_IN_WORKER)).isAdmin()){
			borrowerActionsButton.reset();
			workerActionsButton.reset();
		}
		rentBookButton.reset();
		returnBookButton.reset();
	}

	@Override
	public void updateState(String key, Object value) {

	}

}
