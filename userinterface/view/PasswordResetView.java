/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.view;

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

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.PasswordField;
import userinterface.component.TextField;
import userinterface.message.MessagePanel;
import utilities.Key;
import controller.Controller;

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

	/**
	 * Constructs Reset Password view object and subscribes 
	 * to the Recover Password Transaction model.
	 * @param model The Recover Password Transaction
	 */
	public PasswordResetView(Controller controller) {
		super(controller, "Set Password");

		setBackground(BACKGROUND_COLOR);

		// Set the layout for this panel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Create our GUI components, add them to this panel
		add(createDataEntryFields());
		add(createButtons());

		// Error message area

		// Subscribe the to model events
		controller.subscribe(Key.INPUT_ERROR, this);
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
	 * Create data entry panel for this view.
	 * @return data entry panel
	 */
	private JPanel createDataEntryFields() {
		JPanel dataEntryPanel = new JPanel();
		dataEntryPanel.setLayout(new BoxLayout(dataEntryPanel, BoxLayout.Y_AXIS));
		dataEntryPanel.setBackground(BACKGROUND_COLOR);

		resetCode = new TextField(25);
		dataEntryPanel.add(ViewHelper.formatFieldCenter("Reset Code", resetCode));
		
		password = new PasswordField(25);
		dataEntryPanel.add(ViewHelper.formatFieldCenter("New Password", password));
		
		passwordConfirmation = new PasswordField(25);
		dataEntryPanel.add(ViewHelper.formatFieldCenter("Confirm Password", passwordConfirmation));

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
		buttonPanel.setBackground(BACKGROUND_COLOR);

		// create the buttons, listen for events, add them to the panel
		submitButton = new Button("Submit");
		submitButton.addActionListener(this);
		buttonPanel.add(submitButton);

		buttonPanel.add(new JLabel("     "));
		
		cancelButton = new Button("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);

		return buttonPanel;
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
			messagePanel.displayErrorMessage("Enter the reset code emailed to you!");
			return false;
		}
		if ((password == null) || (password.length() < 6)) {
			messagePanel.displayErrorMessage("Password must be greater than 5 characters long!");
			return false;
		}
		if (!password.equals(passwordConfirm)) {
			messagePanel.displayErrorMessage("Password must match confirmation password!");
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
		messagePanel.clear();

		if (evt.getSource() == cancelButton) {
			controller.stateChangeRequest(Key.RECOVER_PW_COMPLETED, null);
		} else if (evt.getSource() == submitButton) {
			String resetCode = this.resetCode.getText();
			String password = new String(this.password.getPassword());
			String passwordConfirmation = new String(this.passwordConfirmation.getPassword());
			
			if (validate(resetCode, password, passwordConfirmation)) {
				Properties passwordData = new Properties();
				passwordData.setProperty("ResetCode", resetCode);
				passwordData.setProperty("Password", password);
				passwordData.setProperty("PasswordConfirmation", passwordConfirmation);
				controller.stateChangeRequest(Key.RESET_PW, passwordData);
			}

		}

	}

	// ---------------------------------------------------------------------

	/**
	 * Called when events this View subscribed to is changed. Should be used to
	 * update what is displayed in the view.
	 */
	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage(value.toString());
		}

	}

	// ---------------------------------------------------------------------

	/**
	 * Only used for list views.
	 */
	protected void processListSelection(EventObject evt) {
	}
}
