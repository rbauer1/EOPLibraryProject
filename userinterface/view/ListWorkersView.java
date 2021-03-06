package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import userinterface.view.form.Form;
import userinterface.view.form.WorkerSearchForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list workers
 */
public class ListWorkersView extends ListView {

	private static final long serialVersionUID = 3952404276228902079L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Back"};

	/** Entities in the table */
	private List<Worker> workers;

	/** Form to provide search criteria*/
	private Form form;

	/**
	 * Constructs list workers view
	 * @param controller
	 */
	public ListWorkersView(Controller controller) {
		super(controller, "Worker Search", BUTTON_NAMES);

		// Get the operation type and update button
		String operationType = (String) controller.getState(Key.OPERATION_TYPE);
		if(operationType != null){
			buttons.get("Submit").setText(operationType);
		}

		subscribeToController(Key.MESSAGE, Key.WORKER_COLLECTION);
	}

	@Override
	public void afterShown() {
		super.afterShown();
		// Get Workers for initial filter settings
		filter();
	}


	@Override
	protected void buildForm() {
		form = new WorkerSearchForm(this);
		add(form);
	}

	@Override
	protected JTable createTable() {
		return new JTable(new WorkerTableModel(new ArrayList<Worker>()));
	}

	/**
	 * Filters the table by the criteria specified in the form
	 */
	private void filter() {
		form.setAllFieldsEnabled(false);
		controller.stateChangeRequest(Key.FILTER, form.getNonEmptyValues());
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == form) {
			filter();
		} else if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		} else if (source == buttons.get("Submit")) {
			select();
		}
	}

	@Override
	protected void processListSelection() {
		buttons.get("Submit").setEnabled(table.getSelectedRow() >= 0);
	}

	@Override
	protected void select() {
		int rowIndex = table.getSelectedRow();
		if (rowIndex > -1) {
			controller.stateChangeRequest(Key.SELECT_WORKER, workers.get(rowIndex));
		} else {
			messagePanel.displayMessage(MessageType.WARNING, "Warning! Must select a worker from the list!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.WORKER_COLLECTION)) {
			workers = (List<Worker>) value;
			table.setModel(new WorkerTableModel(workers));
			table.repaint();
			form.setAllFieldsEnabled(true);
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
