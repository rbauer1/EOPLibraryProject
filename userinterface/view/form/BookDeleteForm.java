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
 * Form that takes the input of notes for deleting a Book model.
 */
public class BookDeleteForm extends Form {

	private static final long serialVersionUID = 8891254032102434181L;

	/**
	 * Constructs Book Delete Form
	 * @param view
	 */
	public BookDeleteForm(View view) {
		super(view);
	}
	
	@Override
	protected void build() {
		TextField barcodeField = new TextField(16);
		addField("Barcode", barcodeField);
		add(ViewHelper.formatFieldLeft("Barcode", barcodeField));
		
		TextField titleField = new TextField(16);
		addField("Title", titleField);
		add(ViewHelper.formatFieldLeft("Title", titleField));
		
		TextField author1Field = new TextField(16);
		addField("Author1", author1Field);
		add(ViewHelper.formatFieldLeft("Author", author1Field));

		SelectField deleteReasonField = new SelectField(new String[] { "None", "Damaged - Discarded ", "Obsolete - Discarded", "Donated" });
		deleteReasonField.setPreferredSize(new Dimension(185,25));
		addField("DeletionReason", deleteReasonField);
		add(ViewHelper.formatFieldLeft("Reason", deleteReasonField));
				
		TextArea notesField = new TextArea();
		addField("Notes", notesField);
		add(ViewHelper.formatTextAreaFieldLeft("Notes", notesField));	
	}

}
