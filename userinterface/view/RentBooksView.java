package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Book;
import model.Borrower;
import userinterface.ViewHelper;
import userinterface.component.Label;
import userinterface.message.MessageEvent;
import userinterface.view.form.BarcodeForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to rent books
 */
public class RentBooksView extends ListView {

	private static final long serialVersionUID = -7452072411398228893L;

	/** Names of buttons on top under form, Must be in order which you want them to appear */
	private static final String[] TOP_BUTTON_NAMES = {"Add", "Remove"};

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BOTTOM_BUTTON_NAMES = {"Done", "Back"};

	/** Books that are to be rented */
	private List<Book> books;

	/** Form to enter in book barcode */
	private Form barcodeForm;

	private Label rentalsHeading;

	/**
	 * Constructs Rent Books View
	 * @param controller
	 */
	public RentBooksView(Controller controller) {
		super(controller, "Rent Books", BOTTOM_BUTTON_NAMES);
		subscribeToController(Key.BOOK_COLLECTION, Key.BORROWER, Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		messagePanel.clear();
		barcodeForm.reset();
		barcodeForm.requestFocusForDefaultField();
		processListSelection();
	}

	@Override
	protected void buildForm() {
		barcodeForm = new BarcodeForm(this);
		add(barcodeForm);
		add(createButtons(TOP_BUTTON_NAMES));
	}

	@Override
	protected JTable createTable() {
		rentalsHeading = new Label("Books to be Rented by Borrower", true);
		add(ViewHelper.formatLeft(rentalsHeading, 0));
		books = new ArrayList<Book>();
		return new JTable(new BookTableModel(books));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == barcodeForm  || source == buttons.get("Add")) {
			barcodeForm.setAllFieldsEnabled(false);
			buttons.get("Add").setEnabled(false);
			buttons.get("Done").setEnabled(false);
			controller.stateChangeRequest(Key.ADD_BOOK_TO_LIST, barcodeForm.getValues());
		} else if (source == buttons.get("Remove")) {
			removeSelectedBook();
		} else if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListBorrowersView");
		} else if (source == buttons.get("Done")) {
			barcodeForm.setAllFieldsEnabled(false);
			buttons.get("Add").setEnabled(false);
			buttons.get("Done").setEnabled(false);
			controller.stateChangeRequest(Key.RENT_BOOKS, null);
		}
	}

	@Override
	protected void processListSelection() {
		buttons.get("Remove").setEnabled(table.getSelectedRow() >= 0);
	}

	/**
	 * Removes the selected book from the list of books to be rented
	 */
	private void removeSelectedBook() {
		int selectedRow = table.getSelectedRow();
		if(selectedRow > -1) {
			controller.stateChangeRequest(Key.REMOVE_BOOK_FROM_LIST, books.get(selectedRow));
		}
	}

	@Override
	protected void select() {
		//removeSelectedBook();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		barcodeForm.reset();
		barcodeForm.requestFocusForDefaultField();
		barcodeForm.setAllFieldsEnabled(true);
		buttons.get("Add").setEnabled(true);
		buttons.get("Done").setEnabled(books.size() > 0);
		if (key.equals(Key.BOOK_COLLECTION)) {
			books = (List<Book>) value;
			table.setModel(new BookTableModel(books));
			table.repaint();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}else if (key.equals(Key.BORROWER)) {
			Borrower borrower = (Borrower)value;
			String heading = "Books to be Rented by " + borrower.getState("Name") + " (" + borrower.getPrimaryKeyValue() + ")";
			rentalsHeading.setText(heading);
		}
	}
}
