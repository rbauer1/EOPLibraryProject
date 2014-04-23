package userinterface.view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingWorker;

import model.Borrower;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import userinterface.view.form.BorrowerSearchForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list borrowers
 */
public class ListBorrowersView extends ListView {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Back"};

	private static final long serialVersionUID = 3952404276228902079L;

	/** Entities in the table */
	private List<Borrower> borrowers;

	/** Form to provide search criteria*/
	private Form form;

	private SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
		@Override
		public Void doInBackground() {
			filter();
			return null;
		}
	};
	
	/**
	 * Constructs list borrowers view
	 * @param controller
	 */
	public ListBorrowersView(Controller controller) {
		super(controller, "Borrower Search", BUTTON_NAMES);

		// Get the operation type and update button
		String operationType = (String) controller.getState(Key.OPERATION_TYPE);
		if(operationType != null){
			buttons.get("Submit").setText(operationType);
		}

		subscribeToController(Key.MESSAGE, Key.BORROWER_COLLECTION);
	}

	@Override
	public void afterShown() {
		super.afterShown();
		// Get Borrowers for initial filter settings
		swingWorker.execute();
	}


	@Override
	protected void buildForm() {
		form = new BorrowerSearchForm(this);
		add(form);
	}

	@Override
	protected JTable createTable() {
		return new JTable(new BorrowerTableModel(new ArrayList<Borrower>()));
	}

	/**
	 * Filters the table by the criteria specified in the form
	 */
	private void filter() {
		controller.stateChangeRequest(Key.BORROWER_COLLECTION, form.getNonEmptyValues());
	}

	@Override
	public void processAction(Object source) {
		/*
		 * This if-statement will prevent the user from interacting with the
		 * buttons until the SwingWorker has completed its action, which in this
		 * case is performing the query to populate the table. Whether or not we
		 * need it is up for debate
		 */
		if (!swingWorker.isDone()) {
            return;
        }
		
		messagePanel.clear();
		if (source == form) {
			filter();
		} else if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
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
			controller.stateChangeRequest(Key.SELECT_BORROWER, borrowers.get(rowIndex));
		} else {
			messagePanel.displayMessage(MessageType.WARNING, "Warning! Must select a borrower from the list!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.BORROWER_COLLECTION)) {
			borrowers = (List<Borrower>) value;
			table.setModel(new BorrowerTableModel(borrowers));
			table.repaint();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
