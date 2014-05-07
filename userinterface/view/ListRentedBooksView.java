package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Book;
import userinterface.message.MessageEvent;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to list all rented books
 */
public class ListRentedBooksView extends ListView {

	private static final long serialVersionUID = -7452072411398228893L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Export To Excel", "Back"};

	/** Entities in the table */
	private List<Book> books;

	/**
	 * Constructs list rented books
	 * @param controller
	 */
	public ListRentedBooksView(Controller controller) {
		super(controller, "Rented Books", BUTTON_NAMES);
		subscribeToController(Key.BOOK_COLLECTION, Key.MESSAGE);
		controller.stateChangeRequest(Key.REFRESH_LIST, null);
	}

	@Override
	protected void buildForm() {}

	@Override
	protected JTable createTable() {
		books = new ArrayList<Book>();
		return new JTable(new BookTableModel(books));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
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
		if (key.equals(Key.BOOK_COLLECTION)) {
			books = (List<Book>) value;
			table.setModel(new BookTableModel(books));
			table.repaint();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
