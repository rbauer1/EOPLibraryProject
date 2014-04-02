package userinterface;

import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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

import model.Book;
import utilities.Key;

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
	private static final Color requiredFieldLabelColor = new Color(0xA30000);

	// constructor for this class -- takes a model object and
	// gathers all the components into this view
	// ----------------------------------------------------------
	public AddBookView(IModel model) {

		super(model, "AddBookView");
		setLayout(new BorderLayout());
		// create our GUI components, add them to this panel
		add(createTitle(), BorderLayout.NORTH);
		add(createForm(), BorderLayout.CENTER);
		// message area
		statusLog = new MessageView("");
		add(createStatusLog("                          "), BorderLayout.SOUTH);
		myModel.subscribe(Key.INPUT_ERROR, this);
		myModel.subscribe(Key.BOOK_SUBMIT_SUCCESS, this);
		myModel.subscribe(Key.SELECT_BOOK, this);
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

			myModel.stateChangeRequest(Key.SUBMIT_BOOK, newBookProps);
		}
	}

	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			statusLog.displayErrorMessage("There was an error");
		}else if(key.equals(Key.BOOK_SUBMIT_SUCCESS)){
			statusLog.displayMessage("Book added successfully"); 
		}else if(key.equals(Key.SELECT_BOOK)){
			adjustViewForModifyTransaction((Book)value);
		}
	}
	
	private void adjustViewForModifyTransaction(Book b){
		submitButton.setText("Modify");
		populateFields(b);
	}

	private void populateFields(Book b){
		barcodeField.setText((String)b.getState("Barcode"));
		barcodeField.setEnabled(false);
		titleField.setText((String)b.getState("Title"));
		author1Field.setText((String)b.getState("Author1"));
		author2Field.setText((String)b.getState("Author2"));
		publisherField.setText((String)b.getState("Publisher"));
		yearOfPubField.setText((String)b.getState("YearOfPublication"));
		isbnField.setText((String)b.getState("ISBN"));
		suggestedPriceField.setText((String)b.getState("SuggestedPrice"));
		notesArea.setText((String)b.getState("Notes"));
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
		JPanel fieldSubPanel = new BluePanel();
		fieldSubPanel.setLayout(new BoxLayout(fieldSubPanel, BoxLayout.X_AXIS));
		
		
		JLabel requiredValues = new JLabel("* Denotes required value");
		requiredValues.setFont(new Font("Arial", Font.BOLD, 14));
		requiredValues.setForeground(requiredFieldLabelColor);
		
		barcodeField = new JTextField(16);
		barcodeField.setText(" ");
		barcodeField.addActionListener(this);

		author1Field = new JTextField(16);
		author1Field.setText(" ");
		author1Field.addActionListener(this);
		
		publisherField = new JTextField(16);
		publisherField.setText(" ");
		publisherField.addActionListener(this);
		
		isbnField = new JTextField(16);
		isbnField.setText(" ");
		isbnField.addActionListener(this);
		
		String[] choices = { "Good", "Damaged" };
		bookConditionBox = new JComboBox<String>(choices);
		
		JPanel col1 = new BluePanel();
		col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
		col1.add(formatLargeLabel(requiredValues));
		col1.add(formatCurrentPanel("* Barcode:", barcodeField));
		col1.add(formatCurrentPanel("* Author1:", author1Field));
		col1.add(formatCurrentPanel("*  Publisher:", publisherField));
		col1.add(formatCurrentPanel("   ISBN:", isbnField));
		col1.add(formatCurrentPanel("   Condition:", bookConditionBox));

		
		titleField = new JTextField(16);
		titleField.setText(" ");
		titleField.addActionListener(this);

		author2Field = new JTextField(16);
		author2Field.setText(" ");
		author2Field.addActionListener(this);
		
		yearOfPubField = new JTextField(16);
		yearOfPubField.setText(" ");
		yearOfPubField.addActionListener(this);
		
		suggestedPriceField = new CurrencyTextField(16,16);
		suggestedPriceField.addActionListener(this);
		
		
		
		JPanel col2 = new BluePanel();
		col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
		col2.add(formatComponentLarge(new JLabel("")));
		col2.add(formatCurrentPanelLarge("* Title:", titleField));
		col2.add(formatCurrentPanelLarge("   Author2:", author2Field));
		col2.add(formatCurrentPanelLarge("*  Year of Publication:",yearOfPubField));
		col2.add(formatMoneyPanelLarge("*  Suggested Price:", suggestedPriceField));
		col2.add(formatComponentLarge(new JLabel("")));
		
		fieldSubPanel.add(col1);
		fieldSubPanel.add(col2);
		
		notesArea = new JTextArea();
		
		fieldPanel.add(fieldSubPanel);
		fieldPanel.add(formatCurrentPanel("Additional Notes:", new JScrollPane(
		notesArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED)));
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
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessageView(initialMessage);
		return statusLog;
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	// There is no need for this b/c we don't have any tables here.
	protected void processListSelection(EventObject evt) {
	}

}