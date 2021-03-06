/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.view.form;

import java.awt.Dimension;

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.NumericTextField;
import userinterface.component.Panel;
import userinterface.component.PasswordField;
import userinterface.component.PhoneField;
import userinterface.component.SelectField;
import userinterface.component.TextArea;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the input for a Worker model.
 * Can be used for adding of modifying a Worker.
 */
public class WorkerForm extends Form {
	private static final long serialVersionUID = 6701251230827206294L;	
	public static final String PASSORD_FIELD = "NewPassword";
	public static final String CONFIRM_PASSORD_FIELD = "NewPasswordConfirmation";
	/**
	 * Constructs new Worker Form
	 * @param view
	 */
	public WorkerForm(View view) {
		super(view);
	}
	
	@Override
	protected void build() {
		//TODO confirm that date of hire should be date of Add
		Panel fieldColumnsPanel = new Panel();
		fieldColumnsPanel.setLayout(new BoxLayout(fieldColumnsPanel, BoxLayout.X_AXIS));
		
		Panel leftColumn = new Panel();
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(leftColumn);

		NumericTextField bannerIdField = new NumericTextField(16,9);
		bannerIdField.addActionListener(this);
		addField("BannerID", bannerIdField);
		leftColumn.add(ViewHelper.formatFieldLeft("Banner ID", bannerIdField));
		
		PasswordField passwordField = new PasswordField(16);
		passwordField.addActionListener(this);
		addField(PASSORD_FIELD, passwordField);
		leftColumn.add(ViewHelper.formatFieldLeft("Password", passwordField));
		
		PasswordField confirmPasswordField = new PasswordField(16);
		confirmPasswordField.addActionListener(this);
		addField(CONFIRM_PASSORD_FIELD, confirmPasswordField);
		leftColumn.add(ViewHelper.formatFieldLeft("Confirm Password", confirmPasswordField));

		SelectField credentialsField = new SelectField(new String[] { "Ordinary", "Administrator"});
		credentialsField.setPreferredSize(new Dimension(130,25));
		addField("Credentials", credentialsField);
		leftColumn.add(ViewHelper.formatFieldLeft("Credentials", credentialsField));
		
		add(fieldColumnsPanel);
		
		Panel rightColumn = new Panel();

		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		
		TextField firstNameField = new TextField(16);
		firstNameField.addActionListener(this);
		addField("FirstName", firstNameField);
		rightColumn.add(ViewHelper.formatFieldLeft("First Name", firstNameField));
		
		TextField lastNameField = new TextField(16);
		lastNameField.addActionListener(this);
		addField("LastName", lastNameField);
		rightColumn.add(ViewHelper.formatFieldLeft("Last Name", lastNameField));
		
		PhoneField phoneField = new PhoneField();
		phoneField.addActionListener(this);
		addField("ContactPhone", phoneField);
		rightColumn.add(ViewHelper.formatFieldLeft("Phone", phoneField));
		
		TextField emailField = new TextField(16);
		emailField.addActionListener(this);
		addField("Email", emailField);
		rightColumn.add(ViewHelper.formatFieldLeft("Email", emailField));

		fieldColumnsPanel.add(rightColumn);
		
		TextArea notesField = new TextArea();
		addField("Notes", notesField);
		add(ViewHelper.formatTextAreaFieldLeft("Notes", notesField));
	}

}
