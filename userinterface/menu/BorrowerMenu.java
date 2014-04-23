package userinterface.menu;

import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.Button;
import utilities.Key;
import controller.Controller;
import javax.swing.JComponent;

public class BorrowerMenu extends Menu {
	
	/* Buttons */
	private Button addButton;
	private Button modifyButton;
	private Button deleteButton;

	/**
	 * Constructs Borrower menu
	 * @param controller
	 */
	public BorrowerMenu(Controller controller) {
		super(controller, "Borrower Menu");
	}

	@Override
	protected void build() {
		Accordion content = getContent();

		addButton = new Button("Add Borrower", this);
		content.add(addButton);

		modifyButton = new Button("Modify Borrower", this);
		content.add(modifyButton);

		deleteButton = new Button("Delete Borrower", this);
		content.add(deleteButton);
	}

	@Override
	public void processAction(Object source) {
		if (source == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_BORROWER, null);
		} else if (source == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_BORROWER, null);
		} else if (source == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_BORROWER, null);
		}
	}

}
