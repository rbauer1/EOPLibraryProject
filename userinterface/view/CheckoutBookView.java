package userinterface.view;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JTable;

import model.Book;
import model.Rental;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.message.MessageEvent;
import userinterface.view.form.BarcodeForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to search and list books
 */
public class CheckoutBookView extends ListView {

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Done", "Back"};

	private static final long serialVersionUID = -7452072411398228893L;

	/** Entities in the table */
	private Collection<Book> books;

	/** Form to enter in book barcode */
	private Form form;

	/** Button to add book to checkout */
	private Button rentButton;

	/**
	 * Constructs list of rentals
	 * @param controller
	 */
	public CheckoutBookView(Controller controller) {
		super(controller, "Rent Books", BUTTON_NAMES);
		subscribeToController(Key.BOOK_COLLECTION, Key.MESSAGE);
	}

	@Override
	public void afterShown(){
		form.requestFocusForDefaultField();
	}

	@Override
	protected void buildForm() {
		form = new BarcodeForm(this);
		add(form);

		rentButton = new Button("Submit");
		rentButton.addActionListener(this);
		add(ViewHelper.formatCenter(rentButton));
	}

	@Override
	protected JTable createTable() {
		return new JTable(new RentalTableModel(new ArrayList<Rental>()));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == form  || source == rentButton) {
			controller.stateChangeRequest(Key.ADD_BOOK_TO_LIST, form.getValues());
		} else if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListBorrowersView");
		} else if (source == buttons.get("Done")) {
			controller.stateChangeRequest(Key.CHECKOUT_BOOKS, null);
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
			books = (Collection<Book>) value;
			table.setModel(new BookTableModel(books));
			table.repaint();
			form.reset();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
