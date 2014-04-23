package userinterface.menu;

import controller.Controller;
import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.Button;
import utilities.Key;
import javax.swing.JComponent;

public class WorkerMenu extends Menu {
	
	/* Buttons */
	private Button addButton;
	private Button modifyButton;
	private Button deleteButton;

	/**
	 * Constructs Worker menu
	 * @param controller
	 */
	public WorkerMenu(Controller controller) {
		super(controller, "Worker Menu");
	}

	@Override
	protected void build() {
		Accordion content = getContent();

		addButton = new Button("Add Worker", this);
		content.add(addButton);

		modifyButton = new Button("Modify Worker", this);
		content.add(modifyButton);

		deleteButton = new Button("Delete Worker", this);
		content.add(deleteButton);
	}
	
	@Override
	public void processAction(Object source) {
		if (source == addButton) {
			controller.stateChangeRequest(Key.EXECUTE_ADD_WORKER, null);
		} else if (source == modifyButton) {
			controller.stateChangeRequest(Key.EXECUTE_MODIFY_WORKER, null);
		} else if (source == deleteButton) {
			controller.stateChangeRequest(Key.EXECUTE_DELETE_WORKER, null);
		}
	}

}
