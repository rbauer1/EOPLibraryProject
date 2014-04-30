package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Rental;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list books
 */
public class ListRentalsView extends ListView {

	private static final long serialVersionUID = -7452072411398228893L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Back"};

	/** Entities in the table */
	private List<Rental> rentals;

	/**
	 * Constructs list rentals view
	 * @param controller
	 */
	public ListRentalsView(Controller controller) {
		super(controller, "Rental Search", BUTTON_NAMES);

		// Get the operation type and update button
		String operationType = (String) controller.getState(Key.OPERATION_TYPE);
		if(operationType != null){
			buttons.get("Submit").setText(operationType);
		}

		subscribeToController(Key.RENTAL_COLLECTION, Key.REFRESH_LIST, Key.MESSAGE);

	}

	@Override
	public void afterShown() {
		super.afterShown();
	}

	@Override
	protected void buildForm() {}

	@Override
	protected JTable createTable() {
		return new JTable(new RentalTableModel(new ArrayList<Rental>()));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListBorrowersView");
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
			controller.stateChangeRequest(Key.SELECT_RENTAL, rentals.get(rowIndex));
		} else {
			messagePanel.displayMessage(MessageType.WARNING, "Warning! Must select a rental from the list!");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.RENTAL_COLLECTION)) {
			rentals = (List<Rental>) value;
			table.setModel(new RentalTableModel(rentals));
			table.repaint();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
