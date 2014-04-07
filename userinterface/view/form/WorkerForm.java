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
import java.util.Arrays;

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.CurrencyTextField;
import userinterface.component.FormField;
import userinterface.component.Panel;
import userinterface.component.PasswordField;
import userinterface.component.PhoneField;
import userinterface.component.SelectField;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the input for a Book model.
 * Can be used for adding of modifying a Book.
 */
public class WorkerForm extends Form {
	private static final long serialVersionUID = 6701251230827206294L;

	/**
	 * Constructs new Book Form
	 * @param view
	 */
	public WorkerForm(View view) {
		super(view);
	}
	
	@Override
	protected void build() {
		//TODO confirm that date of hire should be date of Add
		//TODO put in required fields message		
		Panel fieldColumnsPanel = new Panel();
		fieldColumnsPanel.setLayout(new BoxLayout(fieldColumnsPanel, BoxLayout.X_AXIS));
		
		Panel leftColumn = new Panel();
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(leftColumn);

		TextField bannerIdField = new TextField(16);
		bannerIdField.addActionListener(this);
		addField("BannerID", bannerIdField);
		leftColumn.add(ViewHelper.formatFieldLeft("Banner ID", bannerIdField));
		
		PasswordField passwordField = new PasswordField(16);
		passwordField.addActionListener(this);
		addField("Password", passwordField);
		leftColumn.add(ViewHelper.formatFieldLeft("Password", passwordField));
		
		PasswordField confirmPasswordField = new PasswordField(16);
		confirmPasswordField.addActionListener(this);
		leftColumn.add(ViewHelper.formatFieldLeft("Confirm Password", confirmPasswordField));

		TextField firstNameField = new TextField(16);
		firstNameField.addActionListener(this);
		addField("FirstName", firstNameField);
		leftColumn.add(ViewHelper.formatFieldLeft("First Name", firstNameField));
		
		TextField lastNameField = new TextField(16);
		lastNameField.addActionListener(this);
		addField("LastName", lastNameField);
		leftColumn.add(ViewHelper.formatFieldLeft("Last Name", lastNameField));
		
		add(fieldColumnsPanel);
		
		Panel rightColumn = new Panel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		
		PhoneField phoneField = new PhoneField();
//		phoneField.addActionListener(this); //TODO figure out about this
		addField("ContactPhone", phoneField);
		rightColumn.add(ViewHelper.formatFieldLeft("Phone Number", phoneField));
		
		TextField emailField = new TextField(16);
		emailField.addActionListener(this);
		addField("Email", emailField);
		rightColumn.add(ViewHelper.formatFieldLeft("Email", emailField));

		SelectField credentialsField = new SelectField(new String[] { "Ordinary", "Administrator" });
		credentialsField.addActionListener(this);
		credentialsField.setPreferredSize(new Dimension(120,25));
		addField("Credentials", credentialsField);
		rightColumn.add(ViewHelper.formatFieldLeft("Credentials", credentialsField));
		
		SelectField statusField = new SelectField(new String[] { "Active", "Inactive" });
		statusField.addActionListener(this);
		addField("ActiveStatus", statusField);
		rightColumn.add(ViewHelper.formatFieldLeft("Status", statusField));
		
		fieldColumnsPanel.add(rightColumn);
	}
	
}
