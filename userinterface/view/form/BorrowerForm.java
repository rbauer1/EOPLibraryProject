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

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.Panel;
import userinterface.component.PhoneField;
import userinterface.component.SelectField;
import userinterface.component.TextArea;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the input for a Book model.
 * Can be used for adding of modifying a Book.
 */
public class BorrowerForm extends Form {

	private static final long serialVersionUID = -2103617588036549056L;

	/**
	 * Constructs new Book Form
	 * @param view
	 */
	public BorrowerForm(View view) {
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
//				phoneField.addActionListener(this); //TODO figure out about this
				addField("ContactPhone", phoneField);
				rightColumn.add(ViewHelper.formatFieldLeft("Phone Number", phoneField));
				
				TextField emailField = new TextField(16);
				emailField.addActionListener(this);
				addField("Email", emailField);
				rightColumn.add(ViewHelper.formatFieldLeft("Email", emailField));
				
//				SelectField activeStatusField = new SelectField(new String[] { "Active", "Inactive" }); //TODO should this even be an option?
//				activeStatusField.addActionListener(this);
//				addField("ActiveStatus", activeStatusField);
//				rightColumn.add(ViewHelper.formatFieldLeft("Status", activeStatusField));
				
//				SelectField borrowerStatusField = new SelectField(new String[] { "Good Standing", "Delinquent" }); //TODO should this even be an option?
//				borrowerStatusField.addActionListener(this);
//				borrowerStatusField.setPreferredSize(new Dimension(130,25));
//				addField("BorrowerStatus", borrowerStatusField);
//				rightColumn.add(ViewHelper.formatFieldLeft("Standing", borrowerStatusField));
				
				TextArea notesField = new TextArea();
				addField("Notes", notesField);
				add(ViewHelper.formatTextAreaFieldLeft("Notes", notesField));	
				
				fieldColumnsPanel.add(rightColumn);
	}

}
