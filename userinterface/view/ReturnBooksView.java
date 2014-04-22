package userinterface.view;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Rental;
import userinterface.ViewHelper;
import userinterface.component.Label;
import userinterface.message.MessageEvent;
import userinterface.view.form.BarcodeForm;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface to return books
 */
public class ReturnBooksView extends View {

	private static final long serialVersionUID = -7452072411398228893L;

	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BOTTOM_BUTTON_NAMES = {"Done", "Back"};

	/** Names of buttons on top under form, Must be in order which you want them to appear */
	private static final String[] TOP_BUTTON_NAMES = {"Add", "Remove"};

	/** List of Rentals that are outstanding for the borrower */
	private List<Rental> outstandingRentals;

	/** Table that holds Rentals that are outstanding for the borrower */
	private JTable outstandingRentalsTable;

	/** List of Rentals that are to be returned by borrower */
	private List<Rental> returnRentals;

	/** Table that holds Rentals that are to be returned by borrower */
	private JTable returnRentalsTable;

	/** Form that takes the barcode as input */
	private Form barcodeForm;

	/**
	 * Constructs return books view
	 * @param controller
	 */
	public ReturnBooksView(Controller controller) {
		super(controller, "Return Books", BOTTOM_BUTTON_NAMES);
		subscribeToController(Key.OUTSTANDING_RENTALS, Key.RETURN_RENTALS, Key.MESSAGE);
	}

	/**
	 * Adds the rental selected in the outstanding rentals table to the returns
	 */
	private void addSelectedRental() {
		int selectedRow = outstandingRentalsTable.getSelectedRow();
		if(selectedRow > -1) {
			controller.stateChangeRequest(Key.ADD_RENTAL_TO_LIST, outstandingRentals.get(selectedRow));
		}
	}

	@Override
	public void afterShown() {
		messagePanel.clear();
		barcodeForm.reset();
		barcodeForm.requestFocusForDefaultField();
		buttons.get("Remove").setEnabled(returnRentalsTable.getSelectedRow() >= 0);;
	}

	@Override
	protected void build(){
		buildForm();
		buildReturnRentalsTable();
		buildOutstandingRentalsTable();
	}

	/**
	 * Builds barcode form and creates upper buttons for adding and removing to returns
	 */
	protected void buildForm() {
		barcodeForm = new BarcodeForm(this);
		add(barcodeForm);
		add(createButtons(TOP_BUTTON_NAMES));
	}

	/**
	 * Builds outstanding rentals table area
	 */
	protected void buildOutstandingRentalsTable() {
		add(ViewHelper.formatLeft(new Label("Outstanding Rentals for Borrower", true), 0));
		outstandingRentals = new ArrayList<Rental>();
		outstandingRentalsTable = new JTable(new RentalTableModel(outstandingRentals));
		outstandingRentalsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		outstandingRentalsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		outstandingRentalsTable.setPreferredScrollableViewportSize(new Dimension(900, 125));
		outstandingRentalsTable.setAutoCreateRowSorter(true);
		outstandingRentalsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == outstandingRentalsTable && e.getClickCount() == 2) {
					addSelectedRental();
				}
			}
		});
		add(new JScrollPane(outstandingRentalsTable));
	}

	/**
	 * Builds return rentals table area
	 */
	protected void buildReturnRentalsTable() {
		add(ViewHelper.formatLeft(new Label("Returns", true), 0));
		returnRentals = new ArrayList<Rental>();
		returnRentalsTable = new JTable(new RentalTableModel(returnRentals));
		returnRentalsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		returnRentalsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		returnRentalsTable.setPreferredScrollableViewportSize(new Dimension(900, 125));
		returnRentalsTable.setAutoCreateRowSorter(true);
		returnRentalsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				buttons.get("Remove").setEnabled(returnRentalsTable.getSelectedRow() >= 0);
			}
		});
		returnRentalsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getSource() == returnRentalsTable && e.getClickCount() == 2) {
					removeSelectedRental();
				}
			}
		});
		add(new JScrollPane(returnRentalsTable));
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Back")) {
			controller.stateChangeRequest(Key.BACK, "ListBorrowersView");
		} else if (source == buttons.get("Done")) {
			controller.stateChangeRequest(Key.RETURN_BOOKS, null);
		} else if (source == barcodeForm || source == buttons.get("Add")) {
			String barcode = barcodeForm.getValues().getProperty("Barcode", "");
			if(barcode.length() > 0) {
				controller.stateChangeRequest(Key.ADD_RENTAL_TO_LIST, barcode);
			}else {
				addSelectedRental();
			}
			barcodeForm.reset();
			barcodeForm.requestFocusForDefaultField();
		} else if (source == buttons.get("Remove")) {
			removeSelectedRental();
		}
	}

	/**
	 * Removes the selected rental in the returns table and places it in the outstanding rentals
	 */
	private void removeSelectedRental() {
		int selectedRow = returnRentalsTable.getSelectedRow();
		if(selectedRow > -1) {
			controller.stateChangeRequest(Key.REMOVE_RENTAL_FROM_LIST, returnRentals.get(selectedRow));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.OUTSTANDING_RENTALS)) {
			outstandingRentals = (List<Rental>) value;
			outstandingRentalsTable.setModel(new RentalTableModel(outstandingRentals));
			outstandingRentalsTable.repaint();
		}else if (key.equals(Key.RETURN_RENTALS)) {
			returnRentals = (List<Rental>) value;
			returnRentalsTable.setModel(new RentalTableModel(returnRentals));
			returnRentalsTable.repaint();
		}else if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
