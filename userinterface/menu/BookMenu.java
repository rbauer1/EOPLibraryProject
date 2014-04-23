package userinterface.menu;

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.Button;
import utilities.Key;
import controller.Controller;
import javax.swing.JComponent;

public class BookMenu extends Menu {
	
	/* Buttons */
	private Button addButton;
	private Button modifyButton;
	private Button deleteButton;
	private Button processLostBookButton;
	private Button listAvailableButton;
	private Button listUnavailableButton;

	/**
	 * Constructs Book menu
	 * @param controller
	 */
	public BookMenu(Controller controller) {
		super(controller, "Book Menu");
	}
	
	/**
	 * Constructs book menu view
	 * @param controller
	 */
	@Override
	protected void build() {
		Accordion content = getContent();

		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
            addButton = new Button("Add Book", this);
            content.add(addButton);

            modifyButton = new Button("Modify Book", this);
            content.add(modifyButton);

            deleteButton = new Button("Delete Book", this);
            content.add(deleteButton);

            processLostBookButton = new Button("Process Lost Book", this);
            content.add(processLostBookButton);

		}

		listAvailableButton = new Button("List Available Books", this);
		content.add(listAvailableButton);

		listUnavailableButton = new Button("List Unavailable Books", this);
		content.add(listUnavailableButton);
	}

	@Override
	public void processAction(Object source) {
		if (source == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_BOOK, null);
		} else if (source == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_BOOK, null);
		} else if (source == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_BOOK, null);
		} else if (source == processLostBookButton) {
			controller.stateChangeRequest(Key.EXECUTE_PROCESS_LOST_BOOK, null);
		}
	}

}
