package userinterface;

import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.EventObject;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import utilities.Key;

//======================================================================
public class AddBookView extends View {
	private static final long serialVersionUID = -6030753682831962753L;

	private MessageView statusLog;

	private JTextField barcodeField;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField yearOfPubField;
	private JTextField isbnField;
	private JTextField suggestedPriceField;
	private JComboBox<String> bookConditionBox;
	private JComboBox<String> bookStatusBox;
	private JTextArea notesArea;
	private JButton submitButton;
	private JButton cancelButton;

	// constructor for this class -- takes a model object and
	// gathers all the components into this view
	// ----------------------------------------------------------
	public AddBookView(IModel model) {

		super(model, "AddBookView");
		setLayout(new BorderLayout());
		// create our GUI components, add them to this panel
		add(createTitle(), BorderLayout.NORTH);
		add(createForm(), BorderLayout.CENTER);
		// Error message area
		add(createStatusMessage("Hello!"), BorderLayout.SOUTH);
		add(createStatusLog("                          "), BorderLayout.SOUTH);
		myModel.subscribe(Key.INPUT_ERROR, this);
		myModel.subscribe(Key.ADD_BOOK_SUCCESS, this);

	}

	public void processAction(EventObject evt) {
		// Always clear the status log for a new action
		clearErrorMessage();

		if (evt.getSource() == cancelButton) {
			myModel.stateChangeRequest(Key.ADD_BOOK_COMPLETED, null);
		}else if (evt.getSource() == submitButton) {
			Properties newBookProps = new Properties();
			newBookProps.put("Barcode", barcodeField.getText().trim());
			newBookProps.put("Title", titleField.getText().trim());
			newBookProps.put("Author1", authorField.getText().trim());
			// TODO figure out a way to make this better
//			newBookProps.put("Author2", authorField.getText().trim());
//			newBookProps.put("Author3", authorField.getText().trim());
//			newBookProps.put("Author4", authorField.getText().trim());
			newBookProps.put("Publisher", publisherField.getText().trim());
			newBookProps.put("YearOfPublication", yearOfPubField.getText().trim());
			newBookProps.put("ISBN", isbnField.getText().trim());
			newBookProps.put("BookCondition", bookConditionBox.getSelectedItem());
			newBookProps.put("SuggestedPrice", suggestedPriceField.getText().trim());
			newBookProps.put("Notes", notesArea.getText());
			newBookProps.put("BookStatus", bookStatusBox.getSelectedItem());

			myModel.stateChangeRequest(Key.SUBMIT_NEW_BOOK, newBookProps);
		}
	}

	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			statusLog.displayErrorMessage(value.toString());
		}else if(key.equals(Key.ADD_BOOK_SUCCESS)){
			statusLog.displayMessage("Book added successfully"); //TODO figure out how to use value
		}
	}

	// --------------------------------------------------------------
	// TODO complete this
	private void resetForm() {
		barcodeField.setText("");
		barcodeField.setEnabled(true);
		barcodeField.setFocusable(true);
		barcodeField.requestFocus(true);
		barcodeField.addActionListener(this);

		bookConditionBox.removeActionListener(this);
		bookConditionBox.setSelectedIndex(0);
		bookConditionBox.setEnabled(false);

		bookStatusBox.setSelectedIndex(0);
		bookStatusBox.setEnabled(false);

		submitButton.setEnabled(false);
		cancelButton.setText("Done");

		notesArea.setText("");
		notesArea.setEnabled(false);
	}

	// ----------------------------------------------------------------
	// Create this View Title with the string specified and formatted
	// to this program standard in the superclass View
	// -----------------------------------------------------------------
	private JPanel createTitle() {
		return formatViewTitle("Add a Book");
	}

	// ---------------------------------------------------------------------
	// Create the actual form that allows the user to input the new data
	// regarding a new tree
	// ---------------------------------------------------------------------
	private JPanel createForm() {
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
		formPanel.setBackground(blue);

		formPanel.add(createFieldPanel());
		formPanel.add(Box.createRigidArea(size));

		JPanel boxesAndNotesPanel = new JPanel();
		boxesAndNotesPanel.setLayout(new BoxLayout(boxesAndNotesPanel, BoxLayout.X_AXIS));
		
		boxesAndNotesPanel.add(createBoxesPanel());
		boxesAndNotesPanel.add(createBookNotesPanel());
		
		formPanel.add(boxesAndNotesPanel);
		
		formPanel.add(Box.createRigidArea(size));
		formPanel.add(createButtonsPanel());

		formPanel.setBorder(formBorder);
		return formPanel;

	}

	// ----------------------------------------------------------------------------------
	private JPanel createFieldPanel() {
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		fieldPanel.setBackground(blue);
		
		barcodeField = new JTextField(16);
		barcodeField.setText(" ");
		barcodeField.addActionListener(this);
		JPanel barcodePanel = formatCurrentPanel("Barcode:", barcodeField);

		titleField = new JTextField(16);
		titleField.setText(" ");
		titleField.addActionListener(this);
		JPanel titlePanel = formatCurrentPanel("Title:", titleField);
		
		JPanel row1 = new JPanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
		row1.add(barcodePanel);
		row1.add(titlePanel);

		authorField = new JTextField(16);
		authorField.setText(" ");
		authorField.addActionListener(this);
		JPanel authorPanel = formatCurrentPanel("Author:", authorField);

		publisherField = new JTextField(16);
		publisherField.setText(" ");
		publisherField.addActionListener(this);
		JPanel publisherPanel = formatCurrentPanel("Publisher:", publisherField);

		JPanel row2 = new JPanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
		row2.add(authorPanel);
		row2.add(publisherPanel);
		
		yearOfPubField = new JTextField(16);
		yearOfPubField.setText(" ");
		yearOfPubField.addActionListener(this);
		JPanel yearOfPubPanel = formatCurrentPanel("Year of Publication:",
				yearOfPubField);

		isbnField = new JTextField(16);
		isbnField.setText(" ");
		isbnField.addActionListener(this);
		JPanel isbnPanel = formatCurrentPanel("ISBN:", isbnField);
		
		JPanel row3 = new JPanel();
		row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
		row3.add(yearOfPubPanel);
		row3.add(isbnPanel);

		// TODO change to restrict to monetary numbers
		suggestedPriceField = new JTextField(16);
		suggestedPriceField.setText(" ");
		suggestedPriceField.addActionListener(this);
		JPanel suggestedPricePanel = formatCurrentPanel("Suggested Price:", suggestedPriceField);
		fieldPanel.add(row1);
		fieldPanel.add(row2);
		fieldPanel.add(row3);
		fieldPanel.add(suggestedPricePanel);

		return fieldPanel;
	}

	// -----------------------------------------------------------------------------------------
	private JPanel createBoxesPanel() {
		JPanel boxes = new JPanel();
		boxes.setLayout(new BoxLayout(boxes, BoxLayout.Y_AXIS));
		String[] status = { "Active", "Lost", "Inactive" };
		bookStatusBox = new JComboBox<String>(status);
		String[] choices = { "Good", "Damaged" };
		bookConditionBox = new JComboBox<String>(choices);
		boxes.add(formatCurrentPanel("Condition:", bookConditionBox));
		boxes.add(formatCurrentPanel("Status:", bookStatusBox));
		return boxes;
	}

	// -----------------------------------------------------------------------------
	private JPanel createBookNotesPanel() {
		notesArea = new JTextArea();
		JScrollPane notesPane = new JScrollPane(notesArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		return formatCurrentPanel("Notes:", notesPane);
	}

	// -------------------------------------------------------------
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(blue);

		// create "raw" JButtons and call superclass View to format
		// the buttons to the program's standard, add them to the panel
		submitButton = new JButton("Add");
		buttonsPanel.add(formatButtonSmall(submitButton));
		buttonsPanel.add(new JLabel("     "));

		cancelButton = new JButton("Back");
		buttonsPanel.add(formatButtonSmall(cancelButton));
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		return buttonsPanel;

	}

	// Create the status log field
	// -------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	// --------------------------------------------------------------------------
	/**
	 * Creates status message panel with the provided message.
	 * @param initialMessage
	 * @return status message panel
	 */
	private JPanel createStatusMessage(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	// There is no need for this b/c we don't have any tables here.
	// ----------------------------------------------------------
	protected void processListSelection(EventObject evt) {
	}

	public void paint(Graphics g) {
		super.paint(g);
		barcodeField.requestFocus(true);
	}

}