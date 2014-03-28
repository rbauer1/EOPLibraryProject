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

/**
 * The Password Reset View for the EOP Library application. Provides the
 * interface for the workers to reset their password with the reset token emailed to them.
 */
public class PasswordResetView extends View {
	private static final long serialVersionUID = -7309149910050009529L;
	
	/** Buttons */
	private JButton submitButton;
	private JButton cancelButton;

	/** Data entry fields */
	private JTextField resetCode;
	private JPasswordField password;
	private JPasswordField passwordConfirmation;

	/** Shows messages for view */
	private MessageView statusMessage;

	// ---------------------------------------------------------------------

	/**
	 * Constructs Reset Password view object and subscribes 
	 * to the Recover Password Transaction model.
	 * @param model The Recover Password Transaction
	 */
	public PasswordResetView(IModel model) {
		super(model, "PasswordResetView");
		System.out.println("PasswordResetView");

		setBackground(blue);

		// Set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Create our GUI components, add them to this panel
		add(createTitle());
		add(createDataEntryFields());
		add(createButtons());

		// Error message area
		add(createStatusMessage(" "));

		// Subscribe the to model events
		myModel.subscribe("InputError", this);
	}

	// ---------------------------------------------------------------------

	/*
	 * Override paint to focus on bannerId when shown.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		resetCode.requestFocus();
	}

	// ---------------------------------------------------------------------

	/**
	 * Create title panel for this view.
	 * @return title panel
	 */
	protected JPanel createTitle() {
		return formatViewTitle("Reset Password");
	}

	// ---------------------------------------------------------------------

	/**
	 * Create data entry panel for this view.
	 * @return data entry panel
	 */
	private JPanel createDataEntryFields() {
		JPanel dataEntryPanel = new JPanel();
		dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));
		dataEntryPanel.setBackground(blue);

		resetCode = new JTextField(25);
		dataEntryPanel.add(formatCurrentPanelCenter("Reset Code", resetCode));
		
		password = new JPasswordField(25);
		dataEntryPanel.add(formatCurrentPanelCenter("Password", password));
		
		passwordConfirmation = new JPasswordField(25);
		dataEntryPanel.add(formatCurrentPanelCenter("Password Confirmation", passwordConfirmation));

		dataEntryPanel.add(Box.createRigidArea(new Dimension(200, 50)));

		return dataEntryPanel;
	}

	// ---------------------------------------------------------------------

	/**
	 * Create button panel for this view.
	 * @return button panel
	 */
	private JPanel createButtons() {
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(blue);

		// create the buttons, listen for events, add them to the panel
		submitButton = new JButton("Submit");
		buttonPanel.add(formatButton(submitButton));

		buttonPanel.add(new JLabel("     "));

		cancelButton = new JButton("Cancel");
		buttonPanel.add(formatButton(cancelButton));

		return buttonPanel;
	}

	// ---------------------------------------------------------------------

	/**
	 * Creates status message panel with the provided message.
	 * @param initialMessage
	 * @return status message panel
	 */
	private JPanel createStatusMessage(String initialMessage) {
		statusMessage = new MessageView(initialMessage);
		return statusMessage;
	}

	// ---------------------------------------------------------------------

	/**
	 * Verifies data inputted is valid.
	 * 
	 * @param bannerId
	 * @return true if input is valid
	 */
	private boolean validate(String resetCode, String password, String passwordConfirm) {
		if ((resetCode == null) || (resetCode.length() == 0)) {
			statusMessage.displayErrorMessage("Enter the reset code emailed to you!");
			return false;
		}
		if ((password == null) || (password.length() < 6)) {
			statusMessage.displayErrorMessage("Password must be greater than 5 characters long!");
			return false;
		}
		if (!password.equals(passwordConfirm)) {
			statusMessage.displayErrorMessage("Password must match confirmation password!");
			return false;
		}
		return true;
	}

	// ---------------------------------------------------------------------

	/**
	 * Called when click or focus lost event occurs on GUI component. Used to
	 * handle button clicks to submit forms or to handle navigation.
	 */
	public void processAction(EventObject evt) {
		statusMessage.clearErrorMessage();

		if (evt.getSource() == cancelButton) {
			myModel.stateChangeRequest("RecoverPasswordTransactionCompleted", null);
		} else if (evt.getSource() == submitButton) {
			String resetCode = this.resetCode.getText();
			String password = new String(this.password.getPassword());
			String passwordConfirmation = new String(this.passwordConfirmation.getPassword());
			
			if (validate(resetCode, password, passwordConfirmation)) {
				Properties passwordData = new Properties();
				passwordData.setProperty("ResetCode", resetCode);
				passwordData.setProperty("Password", password);
				passwordData.setProperty("PasswordConfirmation", passwordConfirmation);
				myModel.stateChangeRequest("ResetPassword", passwordData);
			}

		}

	}

	// ---------------------------------------------------------------------

	/**
	 * Called when events this View subscribed to is changed. Should be used to
	 * update what is displayed in the view.
	 */
	public void updateState(String key, Object value) {
		if (key.equals("InputError") == true) {
			statusMessage.displayErrorMessage(value.toString());
		}

	}

	// ---------------------------------------------------------------------

	/**
	 * Only used for list views.
	 */
	protected void processListSelection(EventObject evt) {
	}
}
