package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

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
	protected Form form;

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
		filter();
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
		form.setAllFieldsEnabled(false);
		controller.stateChangeRequest(Key.FILTER, form.getNonEmptyValues());
	}

	@Override
	public void processAction(Object source) {
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
			form.setAllFieldsEnabled(true);
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
