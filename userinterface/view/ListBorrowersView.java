package userinterface.view;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTable;

import model.Borrower;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import userinterface.view.form.BorrowerSearchForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

public class ListBorrowersView extends ListView {
	
	private static final long serialVersionUID = 3952404276228902079L;
	
	private Button submitButton;
	private Button searchButton;
	private Button cancelButton;

	private List<Borrower> borrowers;

	private String operationType;
	
	private Form form;

	public ListBorrowersView(Controller controller) {
		super(controller, "Borrower Search");

		// Get the operation type
		operationType = (String) controller.getState(Key.OPERATION_TYPE);
				
		controller.subscribe(Key.GET_BORROWER_COLLECTION, this);
		controller.subscribe(Key.REFRESH_LIST, this);
		
		// Get Borrowers for initial filter settings
		filter();
		
		add(createButtonsPanel());
	}
	
	@Override
	protected void buildFilterForm() {
		form = new BorrowerSearchForm(this);
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
		controller.stateChangeRequest(Key.GET_BORROWER_COLLECTION, form.getValues());
	}

	@Override
	public void processAction(EventObject event) {
		messagePanel.clear();
		Object source = event.getSource();

		if (source == searchButton || source == form) {
			filter();
		} else if (source == cancelButton) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		} else if (source == submitButton) {
			select();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.GET_BORROWER_COLLECTION)) {
			borrowers = (List<Borrower>) value;
			table.setModel(new BorrowerTableModel(borrowers));
			table.repaint();			
		}
		if (key.equals(Key.REFRESH_LIST)) {
			filter();		
		}
	}
	
	@Override
	protected JTable createTable() {
		return new JTable(new BorrowerTableModel(new ArrayList<Borrower>()));
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
			controller.stateChangeRequest(Key.SELECT_BORROWER, borrowers.get(rowIndex));
		} else {
			messagePanel.displayMessage("Warning", "Warning! Must select a borrower from the list!");
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
