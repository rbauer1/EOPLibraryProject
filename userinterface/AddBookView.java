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
	private JTextField author1Field;
	private JTextField author2Field;
	private JTextField publisherField;
	private JTextField yearOfPubField;
	private JTextField isbnField;
	private CurrencyTextField suggestedPriceField;
	@SuppressWarnings("rawtypes")
	private JComboBox bookConditionBox;
	private JTextArea notesArea;
	private JButton submitButton;
	private JButton clearButton;
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
			myModel.stateChangeRequest(Key.BACK_TO_BOOK_MENU, null);
		}else if (evt.getSource() == clearButton){
			resetForm();
		}else if (evt.getSource() == submitButton) {
			Properties newBookProps = new Properties();
			newBookProps.put("Barcode", barcodeField.getText().trim());
			newBookProps.put("Title", titleField.getText().trim());
			newBookProps.put("Author1", author1Field.getText().trim());
			newBookProps.put("Author2", author2Field.getText().trim());
//			newBookProps.put("Author3", authorField.getText().trim());
//			newBookProps.put("Author4", authorField.getText().trim());
			newBookProps.put("Publisher", publisherField.getText().trim());
			newBookProps.put("YearOfPublication", yearOfPubField.getText().trim());
			newBookProps.put("ISBN", isbnField.getText().trim());
			newBookProps.put("BookCondition", bookConditionBox.getSelectedItem());
			newBookProps.put("SuggestedPrice", suggestedPriceField.getText().trim());
			newBookProps.put("Notes", notesArea.getText());

			myModel.stateChangeRequest(Key.SUBMIT_NEW_BOOK, newBookProps);
		}
	}

	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			statusLog.displayErrorMessage(value.toString());
		}else if(key.equals(Key.ADD_BOOK_SUCCESS)){
			statusLog.displayMessage("Book added successfully"); 
		}
	}

	// --------------------------------------------------------------
	private void resetForm() {
		barcodeField.setText("");
		titleField.setText("");
		author1Field.setText("");
		author2Field.setText("");
		publisherField.setText("");
		yearOfPubField.setText("");
		isbnField.setText("");
		suggestedPriceField.setText("");
		
		bookConditionBox.setSelectedIndex(0);

		notesArea.setText("");
	}

	/**
	* Create this View Title with the string specified and formatted to this program standard in the superclass View
	**/
	private JPanel createTitle() {
		return formatViewTitle("Add a Book");
	}

	/**
	* Create the actual form that allows the user to input the new data
	* regarding a new tree
	**/
	private JPanel createForm() {
		JPanel formPanel = new BluePanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		formPanel.add(createFieldPanel());
		formPanel.add(Box.createRigidArea(size));

		formPanel.add(createButtonsPanel());

		formPanel.setBorder(formBorder);
		return formPanel;

	}

	// ----------------------------------------------------------------------------------
	private JPanel createFieldPanel() {
		JPanel fieldPanel = new BluePanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		
		barcodeField = new JTextField(16);
		barcodeField.setText(" ");
		barcodeField.addActionListener(this);
		JPanel barcodePanel = formatCurrentPanel("Barcode:", barcodeField);

		titleField = new JTextField(16);
		titleField.setText(" ");
		titleField.addActionListener(this);
		JPanel titlePanel = formatCurrentPanelLarge("Title:", titleField);
		
		JPanel row1 = new BluePanel();
		row1.setLayout(new BoxLayout(row1, BoxLayout.X_AXIS));
		row1.add(barcodePanel);
		row1.add(titlePanel);

		author1Field = new JTextField(16);
		author1Field.setText(" ");
		author1Field.addActionListener(this);
		JPanel author1Panel = formatCurrentPanel("Author1:", author1Field);

		author2Field = new JTextField(16);
		author2Field.setText(" ");
		author2Field.addActionListener(this);
		JPanel author2Panel = formatCurrentPanelLarge("Author2:", author2Field);

		JPanel row2 = new BluePanel();
		row2.setLayout(new BoxLayout(row2, BoxLayout.X_AXIS));
		row2.add(author1Panel);
		row2.add(author2Panel);
		
		publisherField = new JTextField(16);
		publisherField.setText(" ");
		publisherField.addActionListener(this);
		JPanel publisherPanel = formatCurrentPanel("Publisher:", publisherField);
		
		yearOfPubField = new JTextField(16);
		yearOfPubField.setText(" ");
		yearOfPubField.addActionListener(this);
		JPanel yearOfPubPanel = formatCurrentPanelLarge("Year of Publication:",
				yearOfPubField);

		JPanel row3 = new BluePanel();
		row3.setLayout(new BoxLayout(row3, BoxLayout.X_AXIS));
		row3.add(publisherPanel);
		row3.add(yearOfPubPanel);
		
		isbnField = new JTextField(16);
		isbnField.setText(" ");
		isbnField.addActionListener(this);
		JPanel isbnPanel = formatCurrentPanel("ISBN:", isbnField);
		
		suggestedPriceField = new CurrencyTextField(16,16);
		suggestedPriceField.addActionListener(this);
		JPanel suggestedPricePanel = formatMoneyPanelLarge("Suggested Price:", suggestedPriceField);
		
		JPanel row4 = new BluePanel();
		row4.setLayout(new BoxLayout(row4, BoxLayout.X_AXIS));
		row4.add(isbnPanel);
		row4.add(suggestedPricePanel);
		
		
		String[] choices = { "Good", "Damaged" };
		bookConditionBox = new JComboBox<String>(choices);
		
		
		notesArea = new JTextArea();
		JScrollPane notesPane = new JScrollPane(notesArea,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		fieldPanel.add(row1);
		fieldPanel.add(row2);
		fieldPanel.add(row3);
		fieldPanel.add(row4);
		fieldPanel.add(formatCurrentPanel("Condition:", bookConditionBox));
		fieldPanel.add(formatCurrentPanel("Additional Notes:", notesPane));

		return fieldPanel;
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
		
		clearButton = new JButton("Clear Form");
		buttonsPanel.add(formatButtonSmall(clearButton));
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