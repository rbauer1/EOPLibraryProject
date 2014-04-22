package userinterface.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import model.Book;
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

	/**
	 * Constructs Rent Books View
	 * @param controller
	 */
	public RentBooksView(Controller controller) {
		super(controller, "Rent Books", BOTTOM_BUTTON_NAMES);
		subscribeToController(Key.BOOK_COLLECTION, Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		messagePanel.clear();
		barcodeForm.reset();
		barcodeForm.requestFocusForDefaultField();
	}

	@Override
	protected void buildForm() {
		barcodeForm = new BarcodeForm(this);
		add(barcodeForm);
		add(createButtons(TOP_BUTTON_NAMES));
	}

	@Override
	protected JTable createTable() {
		books = new ArrayList<Book>();
		return new JTable(new BookTableModel(books));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == barcodeForm  || source == buttons.get("Add")) {
			controller.stateChangeRequest(Key.ADD_BOOK_TO_LIST, barcodeForm.getValues());
		} else if (source == buttons.get("Remove")) {

		} else if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListBorrowersView");
		} else if (source == buttons.get("Done")) {
			controller.stateChangeRequest(Key.RENT_BOOKS, null);
		}
	}

	@Override
	protected void processListSelection() {
		//TODO maybe do something when selected?
		//buttons.get("Submit").setEnabled(table.getSelectedRow() >= 0);
	}

	@Override
	protected void select() {
		//TODO maybe add select action
		/*int rowIndex = table.getSelectedRow();
		if (rowIndex > -1) {
			controller.stateChangeRequest(Key.SELECT_RENTAL, rentals.get(rowIndex));
		} else {
			messagePanel.displayMessage(MessageType.WARNING, "Warning! Must select a rental from the list!");
		}*/
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.BOOK_COLLECTION)) {
			books = (List<Book>) value;
			table.setModel(new BookTableModel(books));
			table.repaint();
			barcodeForm.reset();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
