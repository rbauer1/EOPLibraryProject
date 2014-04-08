package userinterface.view;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import userinterface.view.form.Form;
import userinterface.view.form.WorkerSearchForm;
import utilities.Key;
import controller.Controller;

public class ListWorkersView extends ListView {
	
	private static final long serialVersionUID = 3952404276228902079L;
	
	private Button submitButton;
	private Button searchButton;
	private Button cancelButton;

	private List<Worker> workers;

	private String operationType;
	
	private Form form;

	public ListWorkersView(Controller controller) {
		super(controller, "Worker Search");

		// Get the operation type
		operationType = (String) controller.getState(Key.OPERATION_TYPE);
				
		controller.subscribe(Key.GET_WORKER_COLLECTION, this);
		controller.subscribe(Key.REFRESH_LIST, this);
		
		// Get workers for initial filter settings
		filter();
		
		add(createButtonsPanel());
	}
	
	@Override
	protected void buildFilterForm() {
		form = new WorkerSearchForm(this);
		add(form);
	}

	private JPanel createButtonsPanel() {
		Panel buttonPanel = new Panel();

		submitButton = new Button(operationType);
		submitButton.addActionListener(this);
		buttonPanel.add(submitButton);		

		cancelButton = new Button("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

		return ViewHelper.formatCenter(buttonPanel);
	}
	
	private void filter() {
		controller.stateChangeRequest(Key.GET_WORKER_COLLECTION, form.getNonEmptyValues());
	}

	@Override
	public void processAction(EventObject event) {
		messagePanel.clear();
		Object source = event.getSource();

		if (source == searchButton || source == form) {
			filter();
		} else if (source == cancelButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		} else if (source == submitButton) {
			select();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.GET_WORKER_COLLECTION)) {
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
		int rowIndex = table.getSelectedRow();
		if (rowIndex < 0) {
			submitButton.setEnabled(false);
		} else {
			submitButton.setEnabled(true);
		}
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
	
	@Override
	public void afterShown() {
		if (table.getSelectedRow() < 0) {
			submitButton.setEnabled(false);
		} else {
			submitButton.setEnabled(true);
		}
	}
}
