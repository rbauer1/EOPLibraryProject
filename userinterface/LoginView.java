// tabs=4
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and students, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
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

// project imports

/** The class containing the Login View for the EOP Library application */
// ==============================================================
public class LoginView extends View {
	private static final long serialVersionUID = 5785171554484853130L;

	// GUI stuff
	private JButton loginButton;
	private JButton exitButton;

	private JTextField bannerId;
	private JPasswordField password;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	// ----------------------------------------------------------
	public LoginView(IModel lib) {

		super(lib);

		setBackground(blue);

		// set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// create our GUI components, add them to this panel
		add(createTitle());
		add(createDataEntryFields());
		add(createNavigationButtons());

		// Error message area
		add(createStatusLog("                                     "));

		// STEP 0: Be sure you tell your model what keys you are interested in
		myModel.subscribe(Key.LOGIN_ERROR, this);

		// populateFields();

	}

	// Override the paint method to ensure we can set the focus when made visible
	// -------------------------------------------------------------
	public void paint(Graphics g) {
		super.paint(g);
		bannerId.requestFocus();

	}

	// Create the labels and fields
	// -------------------------------------------------------------
	protected JPanel createTitle() {
		return formatViewTitle("LOGIN");
	}

	// Create the main data entry fields
	// -------------------------------------------------------------
	private JPanel createDataEntryFields() {
		JPanel temp = new JPanel();
		// set the layout for this panel
		temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
		temp.setBackground(blue);

		bannerId = new JTextField(25);
		temp.add(formatCurrentPanelCenter("Username", bannerId));

		password = new JPasswordField(25);
		password.addActionListener(this);
		temp.add(formatCurrentPanelCenter("Password", password));

		temp.add(Box.createRigidArea(new Dimension(200, 50)));

		return temp;
	}

	// Create the navigation buttons
	// -------------------------------------------------------------
	private JPanel createNavigationButtons() {
		JPanel temp = new JPanel(new FlowLayout(FlowLayout.CENTER)); // default FlowLayout is fine
		temp.setBackground(blue);

		// create the buttons, listen for events, add them to the panel
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		temp.add(formatButton(loginButton));

		temp.add(new JLabel("                "));

		exitButton = new JButton("Exit Application");
		exitButton.addActionListener(this);
		temp.add(formatButton(exitButton));

		return temp;
	}

	// Create the status log field
	// -------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage) {

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	/**
	 * 
	 * This simply checks to see that the Strings are in proper FORMAT - i.e.,
	 * not empty
	 */
	// -------------------------------------------------------------
	private boolean validate(String bannerId, String password) {
		if ((bannerId == null) || (bannerId.length() == 0)) {
			displayErrorMessage("Please enter a valid user name!");
			return false;
		}
		if ((password == null) || (password.length() == 0)) {
			displayErrorMessage("Please enter a valid password!");
			return false;
		}

		return true;
	}

	// IMPRESARIO: Note how we use this method name instead of
	// 'actionPerformed()'
	// now. This is because the super-class View has methods for both action and
	// focus listeners, and both of them delegate to this method. So this method
	// is called when you either have an action (like a button click) or a loss
	// of focus (like tabbing out of a textfield, moving your cursor to
	// something
	// else in the view, etc.)
	// process events generated from our GUI components
	// -------------------------------------------------------------
	public void processAction(EventObject evt) {
		clearErrorMessage();
		
		if (evt.getSource() == exitButton) {
			myModel.stateChangeRequest(Key.EXIT_SYSTEM, null);
		} else if (evt.getSource() == loginButton) {

			System.out.println("event login btn");
			String bannerId = this.bannerId.getText();
			String password = new String(this.password.getPassword());
			
			if (validate(bannerId, password)) {
				Properties workerData = new Properties();
				workerData.setProperty("BannerID", bannerId);
				workerData.setProperty("Password", password);		
				myModel.stateChangeRequest("Login", workerData);
			}

		}

	}

	/**
	 * Update view methods - methods invoked to call the view back when
	 * something needs to be updated on it. For example, if you want to update
	 * the message field with a "Login Error" message if the password and user
	 * name don't match. Should the method(s) written here follow the Observer
	 * pattern?
	 */
	// ---------------------------------------------------------
	public void updateState(String key, Object value) {
		System.out.println(key);
		System.out.println(value.toString());
		if (key.equals(Key.LOGIN_ERROR)) {
			displayErrorMessage(value.toString());
		}

	}

	/**
	 * Display error message
	 */
	// ----------------------------------------------------------
	public void displayErrorMessage(String message) {
		System.out.println(message);
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	// ----------------------------------------------------------
	public void clearErrorMessage() {
		statusLog.clearErrorMessage();
	}

	// -----------------------------------------------------------
	protected void processListSelection(EventObject evt) {
	}
}
