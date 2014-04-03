/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface;

import impresario.IModel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.EventObject;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import utilities.Key;

/** 
 * The Login View for the EOP Library application.
 * Provides the interface for the workers to login to the system. 
 */
public class LoginView extends View {
	private static final long serialVersionUID = 5785171554484853130L;

	/** Buttons */
	private JButton loginButton;
	private JButton forgotPasswordButton;
	private JButton exitButton;

	/** Data entry fields */
	private JTextField bannerIdField;
	private JPasswordField passwordField;

	/** Shows messages for view */
	private MessageView statusMessage;

	//---------------------------------------------------------------------
	
	/**
	 * Constructs Login view object and subscribes to the
	 * 
	 *  Librarian model.
	 * @param model The Librarian
	 */
	public LoginView(IModel model) {
		super(model, "LoginView");

		setBackground(BACKGROUND_COLOR);

		// Set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Create our GUI components, add them to this panel
		add(createTitle());
		add(createDataEntryFields());
		add(createButtons());

		// Error message area
		add(createStatusMessage(" "));

		// Subscribe the to model events
		myModel.subscribe(Key.LOGIN_ERROR, this);
	}
	
	//---------------------------------------------------------------------

	/* 
	 * Override paint to focus on bannerId when shown.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		bannerIdField.requestFocus();

	}

	//---------------------------------------------------------------------
	
	/**
	 * Create title panel for this view.
	 * @return title panel
	 */
	protected JPanel createTitle() {
		return formatViewTitle("Login");
	}
	
	//---------------------------------------------------------------------

	/**
	 * Create data entry panel for this view.
	 * @return data entry panel
	 */
	private JPanel createDataEntryFields() {
		JPanel dataEntryPanel = new JPanel();
		dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));
		dataEntryPanel.setBackground(BACKGROUND_COLOR);

		bannerIdField = new JTextField(25);
		dataEntryPanel.add(formatCurrentPanelCenter("Username", bannerIdField));

		passwordField = new JPasswordField(25);
		dataEntryPanel.add(formatCurrentPanelCenter("Password", passwordField));

		dataEntryPanel.add(Box.createRigidArea(new Dimension(200, 50)));

		return dataEntryPanel;
	}
	
	//---------------------------------------------------------------------

	/**
	 * Create button panel for this view.
	 * @return button panel
	 */
	private JPanel createButtons() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(BACKGROUND_COLOR);

		// create the buttons, listen for events, add them to the panel
		loginButton = new JButton("Login");
		buttonPanel.add(formatButton(loginButton));

		buttonPanel.add(new JLabel("     "));
		
		forgotPasswordButton = new JButton("Forgot Password");
		buttonPanel.add(formatButton(forgotPasswordButton));

		buttonPanel.add(new JLabel("     "));

		exitButton = new JButton("Exit Application");
		buttonPanel.add(formatButton(exitButton));

		return buttonPanel;
	}
	
	//---------------------------------------------------------------------
	
	
	/**
	 * Creates status message panel with the provided message.
	 * @param initialMessage
	 * @return status message panel
	 */
	private JPanel createStatusMessage(String initialMessage) {
		statusMessage = new MessageView(initialMessage);
		return statusMessage;
	}
	
	//---------------------------------------------------------------------


	/**
	 * Verifies the banner Id and password are not null.
	 * @param bannerId
	 * @param password
	 * @return true if input is valid
	 */
	private boolean validate(String bannerId, String password) {
		if ((bannerId == null) || (bannerId.length() == 0)) {
			statusMessage.displayErrorMessage("Please enter a valid user name!");
			return false;
		}
		if ((password == null) || (password.length() == 0)) {
			statusMessage.displayErrorMessage("Please enter a valid password!");
			return false;
		}
		return true;
	}
	
	//---------------------------------------------------------------------

	/**
	 * Called when click or focus lost event occurs on GUI component.
	 * Used to handle button clicks to submit forms or to handle navigation.
	 */
	public void processAction(EventObject evt) {
		statusMessage.clearErrorMessage();
		
		if (evt.getSource() == exitButton) {
			myModel.stateChangeRequest(Key.EXIT_SYSTEM, null);
		} else if (evt.getSource() == forgotPasswordButton) {
			myModel.stateChangeRequest(Key.EXECUTE_RECOVER_PW, null);
		} else if (evt.getSource() == loginButton) {
			String bannerId = bannerIdField.getText();
			String password = new String(passwordField.getPassword());
			bannerIdField.setText("");
			passwordField.setText("");
			if (validate(bannerId, password)) {
				Properties workerData = new Properties();
				workerData.setProperty("BannerID", bannerId);
				workerData.setProperty("Password", password);		
				myModel.stateChangeRequest(Key.LOGIN, workerData);
			}

		}

	}
	
	//---------------------------------------------------------------------

	/**
	 * Called when events this View subscribed to is changed.
	 * Should be used to update what is displayed in the view.
	 */
	public void updateState(String key, Object value) {
		if (key.equals(Key.LOGIN_ERROR)) {
			statusMessage.displayErrorMessage(value.toString());
		}

	}

	//---------------------------------------------------------------------
	
	/**
	 * Only used for list views.
	 */
	protected void processListSelection(EventObject evt) {}
}
