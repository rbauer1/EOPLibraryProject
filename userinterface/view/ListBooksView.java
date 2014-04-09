package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Book;
import userinterface.view.form.BookSearchForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list books
 */
public class ListBooksView extends ListView {
	
	private static final long serialVersionUID = 3952404276228902079L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Back"};
	
	/** Entities in the table */
	private List<Book> books;
	
	/** Form to provide search criteria*/
	private Form form;

	/**
	 * Constructs list books view
	 * @param controller
	 */
	public ListBooksView(Controller controller) {
		super(controller, "Book Search", BUTTON_NAMES);

		// Get the operation type and update button
		String operationType = (String) controller.getState(Key.OPERATION_TYPE);
		if(operationType != null){
			buttons.get("Submit").setText(operationType);
		}
		
		subscribeToController(Key.BOOK_COLLECTION, Key.REFRESH_LIST);
		
		// Get Books for initial filter settings
		filter();
	}
	
	@Override
	protected void buildFilterForm() {
		form = new BookSearchForm(this);
		add(form);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == form) {
			filter();
		} else if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		} else if (source == buttons.get("Submit")) {
			select();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.BOOK_COLLECTION)) {
			books = (List<Book>) value;
			table.setModel(new BookTableModel(books));
			table.repaint();			
		}
		if (key.equals(Key.REFRESH_LIST)) {
			filter();		
		}
	}
	
	@Override
	protected JTable createTable() {
		return new JTable(new BookTableModel(new ArrayList<Book>()));
	}

	@Override
	protected void processListSelection() {
		buttons.get("Submit").setEnabled(table.getSelectedRow() >= 0);
	}
	
	@Override
	protected void select() {
		int rowIndex = table.getSelectedRow();
		if (rowIndex > -1) {
			controller.stateChangeRequest(Key.SELECT_BOOK, books.get(rowIndex));
		} else {
			messagePanel.displayMessage("Warning", "Warning! Must select a book from the list!");
		}
	}
	
	/**
	 * Filters the table by the criteria specified in the form
	 */
	private void filter() {
		controller.stateChangeRequest(Key.BOOK_COLLECTION, form.getNonEmptyValues());
	}
}
