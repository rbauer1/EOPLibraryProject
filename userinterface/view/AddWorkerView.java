package userinterface.view;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import userinterface.message.MessagePanel;
import utilities.DateUtil;
import utilities.Key;

//======================================================================
public class AddWorkerView extends View {
	private static final long serialVersionUID = -6030753682831962753L;

	private MessagePanel statusLog;

	private JTextField bannerField;
	private JTextField firstNameField;
	private JPasswordField passwordField;
	private JTextField lastNameField;
	private JTextField emailField;
	private JTextField phoneField;
	@SuppressWarnings("rawtypes")
	private JComboBox credentialsBox;
	@SuppressWarnings("rawtypes")
	private JComboBox yearBox;
	@SuppressWarnings("rawtypes")
	private JComboBox monthBox;
	@SuppressWarnings("rawtypes")
	private JComboBox dayBox;
	private JButton submitButton;
	private JButton clearButton;
	private JButton cancelButton;
	private static final Color requiredFieldLabelColor = new Color(0xA30000);

	// constructor for this class -- takes a model object and
	// gathers all the components into this view
	// ----------------------------------------------------------
	public AddWorkerView(IModel model) {

		super(model, "AddBookView");
		setLayout(new BorderLayout());
		// create our GUI components, add them to this panel
		add(createTitle(), BorderLayout.NORTH);
		add(createForm(), BorderLayout.CENTER);
		// message area
		statusLog = new MessagePanel("");
		add(createStatusLog("                          "), BorderLayout.SOUTH);
		controller.subscribe(Key.INPUT_ERROR, this);
		controller.subscribe(Key.WORKER_SUBMIT_SUCCESS, this);

	}

	public void processAction(EventObject evt) {
		// Always clear the status log for a new action
		clearErrorMessage();

		if (evt.getSource() == cancelButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		}else if (evt.getSource() == clearButton){
			resetForm();
		}else if (evt.getSource() == submitButton) {
			Properties newWorkerProps = new Properties();
			newWorkerProps.put("BannerID", bannerField.getText().trim());
			newWorkerProps.put("FirstName", firstNameField.getText().trim());
			newWorkerProps.put("Password", new String(passwordField.getPassword()));
			newWorkerProps.put("LastName", lastNameField.getText().trim());
			newWorkerProps.put("Email", emailField.getText().trim());
			newWorkerProps.put("ContactPhone", phoneField.getText().trim());
			newWorkerProps.put("DateOfHire", getDate());
			newWorkerProps.put("Credentials", credentialsBox.getSelectedItem());
			newWorkerProps.put("DateOfLatestCredentialsStatus", DateUtil.getDate());
			newWorkerProps.put("DateOfLastUpdate", DateUtil.getDate());
			newWorkerProps.put("ActiveStatus", "Active");

			controller.stateChangeRequest(Key.SUBMIT_WORKER, newWorkerProps);
		}
	}

	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			statusLog.displayErrorMessage("There was an error");
		}else if(key.equals(Key.WORKER_SUBMIT_SUCCESS)){
			statusLog.displayMessage("Success", "Book added successfully"); 
		}
	}

	// --------------------------------------------------------------
	private void resetForm() {
		bannerField.setText("");
		firstNameField.setText("");
		passwordField.setText("");
		lastNameField.setText("");
		emailField.setText("");
		phoneField.setText("");
		
		credentialsBox.setSelectedIndex(0);
		updateDate();

	}

	/**
	* Create this View Title with the string specified and formatted to this program standard in the superclass View
	**/
	private JPanel createTitle() {
		return formatViewTitle("Add a Woker");
	}

	/**
	* Create the actual form that allows the user to input the new data
	* regarding a new tree
	**/
	private JPanel createForm() {
		JPanel formPanel = new BluePanel();
		formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

		formPanel.add(createFieldPanel());
		formPanel.add(Box.createRigidArea(SIZE));

		formPanel.add(createButtonsPanel());

		formPanel.setBorder(FORM_BORDER);
		return formPanel;

	}

	// ----------------------------------------------------------------------------------
	private JPanel createFieldPanel() {
		JPanel fieldPanel = new BluePanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
		JPanel fieldSubPanel = new BluePanel();
		fieldSubPanel.setLayout(new BoxLayout(fieldSubPanel, BoxLayout.X_AXIS));
		JPanel dropDownSubPanel = new BluePanel();
		dropDownSubPanel.setLayout(new BoxLayout(dropDownSubPanel, BoxLayout.X_AXIS));
		
		
		JLabel requiredValues = new JLabel("* Denotes required value");
		requiredValues.setFont(new Font("Arial", Font.BOLD, 14));
		requiredValues.setForeground(requiredFieldLabelColor);
		
		bannerField = new JTextField(16);
		bannerField.setText("");
		bannerField.addActionListener(this);

		passwordField = new JPasswordField(16);
		passwordField.setText("");
		passwordField.addActionListener(this);
		
		emailField = new JTextField(16);
		emailField.setText("");
		emailField.addActionListener(this);
		
		updateDate();
		dropDownSubPanel.add(monthBox);
		dropDownSubPanel.add(dayBox);
		dropDownSubPanel.add(yearBox);
		
		String[] choices = { "Ordinary", "Administrator" };
		credentialsBox = new JComboBox<String>(choices);
		
		JPanel col1 = new BluePanel();
		col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
		col1.add(formatLargeLabel(requiredValues));
		col1.add(formatCurrentPanel("*  Banner ID:", bannerField));
		col1.add(formatCurrentPanel("*  Password:", passwordField));
		col1.add(formatCurrentPanel("*  Email:", emailField));
		col1.add(formatCurrentPanel("   Date of hire:", dropDownSubPanel));
		col1.add(formatCurrentPanel("   Credentials:", credentialsBox));

		
		firstNameField = new JTextField(16);
		firstNameField.setText(" ");
		firstNameField.addActionListener(this);

		lastNameField = new JTextField(16);
		lastNameField.setText(" ");
		lastNameField.addActionListener(this);
		
		phoneField = new JTextField(16);
		phoneField.setText(" ");
		phoneField.addActionListener(this);
		
		//TODO Fix Phone
//		phone1 = new NumericTextField(3, 3);
//		phonePanel.add(formatCurrentPanel("Phone:", phone1));
//		TextPrompt tp5 = new TextPrompt(" XXX", phone1);
//		phone1.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
//		
//		JLabel space_2 = new JLabel("- ");
//		phonePanel.add(space_2);
//		
//		phone2 = new NumericTextField(3, 3);
//		phonePanel.add(formatComponent(phone2));
//		TextPrompt tp6 = new TextPrompt(" XXX", phone2);
//		phone2.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
//		
//		JLabel space_3 = new JLabel(" - ");
//		phonePanel.add(space_3);
//		
//		phone3 = new NumericTextField(4, 4);
//		phonePanel.add(formatComponent(phone3));
//		TextPrompt tp7 = new TextPrompt(" XXXX", phone3);
//		phone3.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
//		entryFieldsPanel.add(phonePanel);
		
		JPanel col2 = new BluePanel();
		col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
		col2.add(formatComponentLarge(new JLabel("")));
		col2.add(formatCurrentPanelLarge("*  First Name:", firstNameField));
		col2.add(formatCurrentPanelLarge("*  Last Name:", lastNameField));
		col2.add(formatCurrentPanelLarge("*  Phone Number:",phoneField));
		col2.add(formatComponentLarge(new JLabel("")));
		
		fieldSubPanel.add(col1);
		fieldSubPanel.add(col2);
		
		fieldPanel.add(fieldSubPanel);
		return fieldPanel;
	}


	// -------------------------------------------------------------
	private JPanel createButtonsPanel() {
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(BACKGROUND_COLOR);

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
	// Create the date dropdowns
	// -------------------------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateDate() {
		//TODO parse current date and set the dropboxes
		String currentDate = DateUtil.getDate();
		String tempYear = currentDate.substring(0,4);
		String tempMonth = currentDate.substring(5,7);
		String tempDay = currentDate.substring(8, 10);
		
		String[] year = { "1999", "2000", "2001", "2002", 
				"2003", "2004", "2005", "2006", 
				"2007", "2008", "2009", "2010", 
				"2011", "2012", "2013", "2014", 
				"2015", "2016", "2017", "2018", 
				"2019", "2020", "2021", "2022", 
				"2023", "2024", "2025", "2026", 
				"2027", "2028", "2029", "2030",};
		yearBox = new JComboBox(year);
		yearBox.setSelectedItem(tempYear);

		String[] month = { "January", "February", "March", 
						"April", "May", "June", 
						"July", "August", "September", 
						"October", "November", "December"};
		monthBox = new JComboBox(month);
		monthBox.setSelectedItem(month[Integer.parseInt(tempMonth)-1]);

		String[] day = { "01", "02", "03", "04", "05", "06", 
						"07", "08", "09", "10", "11", "12", 
						"13", "14", "15", "16", "17", "18", 
						"19", "20", "21", "22", "23", "24", 
						"25", "26", "27", "28", "29", "30", 
						"31",};
		dayBox = new JComboBox(day);
		dayBox.setSelectedItem(tempDay);
	}
	// Create the date dropdowns
	// -------------------------------------------------------------
	private String getDate()
	{
		String date = "";
		date = yearBox.getSelectedItem() + "-" + 
				"" + monthBox.getSelectedIndex() + 1 + "-" + 
				dayBox.getSelectedItem();
		return date;
	}
	
	// Create the status log field
	// -------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage) {
		statusLog = new MessagePanel(initialMessage);
		return statusLog;
	}

	public void displayErrorMessage(String message) {
		statusLog.displayErrorMessage(message);
	}

	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clear();
	}

	// There is no need for this b/c we don't have any tables here.
	// ----------------------------------------------------------
	protected void processListSelection(EventObject evt) {
	}

}