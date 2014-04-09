package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Worker;
import userinterface.view.form.WorkerSearchForm;
import userinterface.view.form.Form;
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
		
		subscribeToController(Key.WORKER_COLLECTION, Key.REFRESH_LIST);
		
		// Get Workers for initial filter settings
		filter();
	}
	
	@Override
	protected void buildFilterForm() {
		form = new WorkerSearchForm(this);
		add(form);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.WORKER_COLLECTION)) {
			workers = (List<Worker>) value;
			table.setModel(new WorkerTableModel(workers));
			table.repaint();			
		}
		if (key.equals(Key.REFRESH_LIST)) {
			filter();		
		}
	}
	
	@Override
	protected JTable createTable() {
		return new JTable(new WorkerTableModel(new ArrayList<Worker>()));
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
			messagePanel.displayMessage("Warning", "Warning! Must select a worker from the list!");
		}
	}
	
	/**
	 * Filters the table by the criteria specified in the form
	 */
	private void filter() {
		controller.stateChangeRequest(Key.WORKER_COLLECTION, form.getNonEmptyValues());
	}
}
