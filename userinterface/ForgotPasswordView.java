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
import javax.swing.JTextField;

import utilities.Key;

/**
 * The Forgot Password View for the EOP Library application. Provides the
 * interface for the workers to recover their password to the system.
 */
public class ForgotPasswordView extends View {
	private static final long serialVersionUID = 1169974525395804659L;

	/** Buttons */
	private JButton submitButton;
	private JButton cancelButton;

	/** Data entry fields */
	private JTextField bannerId;

	/** Shows messages for view */
	private MessageView statusMessage;

	// ---------------------------------------------------------------------

	/**
	 * Constructs Forgot Password view object and subscribes 
	 * to the Recover Password Transaction model.
	 * @param model The Recover Password Transaction
	 */
	public ForgotPasswordView(IModel model) {
		super(model, "ForgotPasswordView");

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
		myModel.subscribe(Key.INPUT_ERROR, this);
	}

	// ---------------------------------------------------------------------

	/*
	 * Override paint to focus on bannerId when shown.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		bannerId.requestFocus();
	}

	// ---------------------------------------------------------------------

	/**
	 * Create title panel for this view.
	 * @return title panel
	 */
	protected JPanel createTitle() {
		return formatViewTitle("Forgot Password");
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

		bannerId = new JTextField(25);
		dataEntryPanel.add(formatCurrentPanelCenter("Username", bannerId));

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
	 * Verifies the banner Id is not empty.
	 * 
	 * @param bannerId
	 * @return true if input is valid
	 */
	private boolean validate(String bannerId) {
		if ((bannerId == null) || (bannerId.length() == 0)) {
			statusMessage.displayErrorMessage("Please enter a valid user name1!");
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
			myModel.stateChangeRequest(Key.RECOVER_PW_COMPLETED, null);
		} else if (evt.getSource() == submitButton) {
			String bannerId = this.bannerId.getText();
			if (validate(bannerId)) {
				Properties workerData = new Properties();
				workerData.setProperty("BannerID", bannerId);
				myModel.stateChangeRequest(Key.REQUEST_RESET_TOKEN, workerData);
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
