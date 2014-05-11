package userinterface.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;

import model.Worker;
import userinterface.MainFrame;
import userinterface.component.Accordion;
import userinterface.component.Button;
import userinterface.component.Panel;
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
	
	/** Color of the bottom border of the header */
	private static final Color SEPARATOR_COLOR = new Color(0x000000);
	
	/** Font Color of the title */
	private static final Color TITLE_FONT_COLOR = new Color(0x695D54);
	
	public static final int WIDTH = (int)((double)MainFrame.WIDTH / 4.5);
	public static final int HEIGHT = MainFrame.HEIGHT;
	
	private static final ButtonSet menuSet = new ButtonSet();
	private Accordion accordion; //TODO is this relevant anymore?
	
	/* Buttons */
	private FButton bookActionsButton;
	private FButton borrowerActionsButton;
	private FButton rentBookButton;
	private FButton returnBookButton;
	private FButton workerActionsButton;
	
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
	
	public void add(String but, JComponent[] subs) {
		Button b = new Button(but);
		Accordion container = new Accordion();
		
		for (JComponent sub : subs) {
			container.add(sub);
		}
		accordion.add(b, container);
	}
	
	public void add(MButton but, JComponent container) {
		accordion.add(but, container);
	}
	
	public void fit() {
		accordion.fit();
	}

	@Override
	public void updateState(String key, Object value) {
		
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
		}
	}
	
	@Override
	protected void build() {
		accordion = new Accordion();

		setLayout(new BorderLayout(0,5));
		setBackground(BACKGROUND_COLOR);
		Utils.setAllSize(this, WIDTH, HEIGHT);
		
		
		
		accordion.setBackground(BACKGROUND_COLOR);
		add(accordion, BorderLayout.PAGE_START);

		bookActionsButton = new MButton("Book Menu", Icons.BOOK, this);
		add(bookActionsButton);
		
		Panel separator = new Panel(View.BACKGROUND_COLOR); 
		Utils.setAllSize(separator, WIDTH, 2);
		add(separator);

		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
			borrowerActionsButton = new MButton("Borrower Menu", Icons.BORROWER, this);
			add(borrowerActionsButton);

			separator = new Panel(View.BACKGROUND_COLOR); 
			Utils.setAllSize(separator, WIDTH, 2);
			add(separator);

			workerActionsButton = new MButton("Workers Menu", Icons.WORKER, this);
			add(workerActionsButton);
			
			separator = new Panel(View.BACKGROUND_COLOR); 
			Utils.setAllSize(separator, WIDTH, 2);
			add(separator);
		}

		rentBookButton = new MButton("Rent a Book", Icons.RENT_BOOK, this);
		add(rentBookButton);
		
		separator = new Panel(View.BACKGROUND_COLOR); 
		Utils.setAllSize(separator, WIDTH, 2);
		add(separator);

		returnBookButton = new MButton("Return a Book", Icons.RETURN_BOOK, this);
		add(returnBookButton);
		
		fit();
	}

}
