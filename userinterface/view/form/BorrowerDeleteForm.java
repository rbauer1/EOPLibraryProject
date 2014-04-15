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

import userinterface.ViewHelper;
import userinterface.component.SelectField;
import userinterface.component.TextArea;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the input of notes for deleting a Borrower model.
 */
public class BorrowerDeleteForm extends Form {

	private static final long serialVersionUID = 8891254032102434181L;

	/**
	 * Constructs Borrower Delete Form
	 * @param view
	 */
	public BorrowerDeleteForm(View view) {
		super(view);
	}
	
	@Override
	protected void build() {
		TextField barcodeField = new TextField(16);
		addField("BannerID", barcodeField);
		add(ViewHelper.formatFieldLeft("Banner ID", barcodeField));
		
		TextField firstNameField = new TextField(16);
		addField("FirstName", firstNameField);
		add(ViewHelper.formatFieldLeft("First Name", firstNameField));
		
		TextField lastNameField = new TextField(16);
		addField("LastName", lastNameField);
		add(ViewHelper.formatFieldLeft("Last Name", lastNameField));

		SelectField deleteReasonField = new SelectField(new String[] { "Other", "Graduated", "No Longer in Program", "Transferred" });
		deleteReasonField.setPreferredSize(new Dimension(185,25));
		addField("DeletionReason", deleteReasonField);
		add(ViewHelper.formatFieldLeft("Reason", deleteReasonField));
				
		TextArea notesField = new TextArea();
		addField("Notes", notesField);
		add(ViewHelper.formatTextAreaFieldLeft("Additional Notes", notesField));	
	}

}
