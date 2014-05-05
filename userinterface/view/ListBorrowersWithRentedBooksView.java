package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Borrower;
import userinterface.message.MessageEvent;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to list all rented books
 */
public class ListBorrowersWithRentedBooksView extends ListView {

	private static final long serialVersionUID = -7452072411398228893L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Export To Excel", "Back"};

	/** Entities in the table */
	private List<Borrower> borrowers;

	/**
	 * Constructs list rented books
	 * @param controller
	 */
	public ListBorrowersWithRentedBooksView(Controller controller) {
		super(controller, "Borrowers With Outstanding Books", BUTTON_NAMES);
		subscribeToController(Key.BORROWER_COLLECTION, Key.MESSAGE);
		controller.stateChangeRequest(Key.REFRESH_LIST, null);
	}

	@Override
	protected void buildForm() {}

	@Override
	protected JTable createTable() {
		borrowers = new ArrayList<Borrower>();
		return new JTable(new BorrowerTableModel(borrowers));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BORROWER_MENU, null);
		}else if (source == buttons.get("Export To Excel")){
			controller.stateChangeRequest(Key.EXPORT_TO_EXCEL, null);
		}
	}

	@Override
	protected void processListSelection() {}

	@Override
	protected void select() {}

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
