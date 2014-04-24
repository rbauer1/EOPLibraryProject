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
import java.util.Properties;

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.NumericTextField;
import userinterface.component.Panel;
import userinterface.component.PhoneField;
import userinterface.component.SelectField;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Search Form to input search data for workers
 */
public class WorkerSearchForm extends Form {

	private static final long serialVersionUID = -6075045845780411636L;

	/** button that submits this form */
	private Button searchButton;

	/**
	 * Constructs new Worker Search Form
	 * @param view
	 */
	public WorkerSearchForm(View view) {
		super(view);
	}

	@Override
	protected void build() {
		Panel fieldColumnsPanel = new Panel();
		fieldColumnsPanel.setLayout(new BoxLayout(fieldColumnsPanel, BoxLayout.X_AXIS));
		add(fieldColumnsPanel);

		Panel leftColumn = new Panel();
		leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(leftColumn);

		NumericTextField bannerIdField = new NumericTextField(16, 9);
		bannerIdField.addActionListener(this);
		addField("BannerID", bannerIdField);
		leftColumn.add(ViewHelper.formatFieldLeft("Banner ID", bannerIdField));

		TextField firstNameField = new TextField(16);
		firstNameField.addActionListener(this);
		addField("FirstName", firstNameField);
		leftColumn.add(ViewHelper.formatFieldLeft("First Name", firstNameField));

		TextField lastNameField = new TextField(16);
		lastNameField.addActionListener(this);
		addField("LastName", lastNameField);
		leftColumn.add(ViewHelper.formatFieldLeft("Last Name", lastNameField));

		TextField emailField = new TextField(16);
		emailField.addActionListener(this);
		addField("Email", emailField);
		leftColumn.add(ViewHelper.formatFieldLeft("Email", emailField));

		Panel rightColumn = new Panel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(rightColumn);

		PhoneField phoneField = new PhoneField();
		phoneField.addActionListener(this);
		addField("ContactPhone", phoneField);
		rightColumn.add(ViewHelper.formatFieldLeft("Phone", phoneField));

		SelectField credentialsField = new SelectField(new String[] { "Any", "Ordinary", "Administrator"});
		credentialsField.addActionListener(this);
		credentialsField.setPreferredSize(new Dimension(130,25));
		addField("Credentials", credentialsField);
		rightColumn.add(ViewHelper.formatFieldLeft("Credentials", credentialsField));

		SelectField statusField = new SelectField(new String[] { "Active", "Inactive", "Any"});
		statusField.addActionListener(this);
		statusField.setPreferredSize(new Dimension(130,25));
		addField("Status", statusField);
		rightColumn.add(ViewHelper.formatFieldLeft("Status", statusField));

		rightColumn.add(ViewHelper.createPlaceHolder());

		searchButton = new Button("Search");
		searchButton.addActionListener(this);
		add(ViewHelper.formatCenter(searchButton));
	}

	@Override
	protected Properties filterValues(Properties values){
		if(values.getProperty("Credentials", "").equals("Any")){
			values.remove("Credentials");
		}
		if(values.getProperty("Status", "").equals("Any")){
			values.remove("Status");
		}
		return values;
	}

	@Override
	public void setAllFieldsEnabled(boolean enabled){
		super.setAllFieldsEnabled(enabled);
		searchButton.setEnabled(enabled);
	}
}
