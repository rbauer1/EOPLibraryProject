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

import java.util.Properties;

import javax.swing.JTextField;

import userinterface.ViewHelper;
import userinterface.component.TextField;
import utilities.Key;
import controller.Controller;

/**
 * The Forgot Password View for the EOP Library application. Provides the
 * interface for the workers to recover their password to the system.
 */
public class ForgotPasswordView extends View {
	
	private static final long serialVersionUID = 1169974525395804659L;
	
	/** Names of buttons on bottom, Must be in order which you want them to appear */
	private static final String[] BUTTON_NAMES = {"Submit", "Cancel"};

	/** Data entry fields */
	private JTextField bannerIdField;


	/**
	 * Constructs Forgot Password view
	 * @param controller
	 */
	public ForgotPasswordView(Controller controller) {
		super(controller, "Reset Password", BUTTON_NAMES);
		subscribeToController(Key.INPUT_ERROR);
	}

	@Override
	protected void build() {
		bannerIdField = new TextField(25);
		add(ViewHelper.formatFieldLeft("Banner ID", bannerIdField));
	}
	
	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == buttons.get("Cancel")) {
			controller.stateChangeRequest(Key.RECOVER_PW_COMPLETED, null);
		} else if (source == buttons.get("Submit")) {
			String bannerId = bannerIdField.getText();
			if (validate(bannerId)) {
				Properties workerData = new Properties();
				workerData.setProperty("BannerID", bannerId);
				controller.stateChangeRequest(Key.REQUEST_RESET_TOKEN, workerData);
			}
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage(value.toString());
		}

	}
	
	@Override
	public void afterShown(){
		bannerIdField.requestFocusInWindow();
	}
	
	

	/**
	 * Verifies the banner Id is not empty.
	 * 
	 * @param bannerId
	 * @return true if input is valid
	 */
	private boolean validate(String bannerId) {
		if ((bannerId == null) || (bannerId.length() == 0)) {
			messagePanel.displayErrorMessage("Please enter a valid Banner ID!");
			return false;
		}
		return true;
	}



}
