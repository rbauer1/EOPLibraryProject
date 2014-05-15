package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Borrower;
import userinterface.message.MessageEvent;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list borrowers
 */
public class ListDelinquentBorrowersView extends ListView {

	/**
	 * Names of buttons on bottom, Must be in order which you want them to
	 * appear
	 */
	private static final String[] BUTTON_NAMES = { "Back" };

	private static final long serialVersionUID = 3952404276228902079L;

	/** Entities in the table */
	private List<Borrower> borrowers;

	/** Form to provide search criteria */
	protected Form form;

	/**
	 * Constructs list borrowers view
	 * 
	 * @param controller
	 */
	public ListDelinquentBorrowersView(Controller controller) {
		super(controller, "Delinquent Borrowers", BUTTON_NAMES);
		
		subscribeToController(Key.DELINQUENT_BORROWERS_COLLECTION, Key.MESSAGE);
	}
	
	@Override
	public void afterShown(){
		controller.stateChangeRequest(Key.REFRESH_LIST, null);
	}

	@Override
	protected void buildForm() {

	}

	@Override
	protected JTable createTable() {
		borrowers = new ArrayList<Borrower>();
		return new JTable(new BorrowerTableModel(borrowers));
	}

	/**
	 * Filters the table by the criteria specified in the form
	 */
	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		}
	}

	@Override
	protected void processListSelection() {
	}

	@Override
	protected void select() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.DELINQUENT_BORROWERS_COLLECTION)) {
			borrowers = (List<Borrower>) value;
			table.setModel(new BorrowerTableModel(borrowers));
			table.repaint();
		} else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent) value);
		}
	}
}
