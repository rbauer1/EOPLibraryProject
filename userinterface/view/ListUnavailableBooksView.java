package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Book;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list books
 */
public class ListUnavailableBooksView extends ListView {
	
	private static final long serialVersionUID = -7452072411398228893L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Back"};
	
	/** Entities in the table */
	private List<Book> rentals;
	
	/**
	 * Constructs list rentals view
	 * @param controller
	 */
	public ListUnavailableBooksView(Controller controller) {
		super(controller, "Rental List", BUTTON_NAMES);

		// Get the operation type and update button
		
		
		subscribeToController(Key.BOOK_COLLECTION, Key.REFRESH_LIST, Key.MESSAGE);
		controller.stateChangeRequest(Key.BOOK_COLLECTION, null);
		
	}
	
	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		} 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.BOOK_COLLECTION)) {
			rentals = (List<Book>) value;
			table.setModel(new BookTableModel(rentals));
			table.repaint();			
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
	
	@Override
	protected JTable createTable() {
		return new JTable(new BookTableModel(new ArrayList<Book>()));
	}

	@Override
	protected void processListSelection() {
	}
	
	@Override
	protected void select() {}
	
	@Override
	protected void buildForm() {} 
}
