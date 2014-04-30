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

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.CurrencyTextField;
import userinterface.component.NumericTextField;
import userinterface.component.Panel;
import userinterface.component.TextArea;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that represents a Rental Model
 */
public class RentalForm extends Form {
	private static final long serialVersionUID = -5569243490261183491L;

	/**
	 * Constructs Book Delete Form
	 * @param view
	 */
	public RentalForm(View view) {
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

		NumericTextField bannerIdField = new NumericTextField(16,9);
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

		Panel rightColumn = new Panel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(rightColumn);

		TextField barcodeField = new TextField(16);
		addField("Barcode", barcodeField);
		rightColumn.add(ViewHelper.formatFieldLeft("Barcode", barcodeField));

		TextField titleField = new TextField(16);
		addField("Title", titleField);
		rightColumn.add(ViewHelper.formatFieldLeft("Title", titleField));

		TextField author1Field = new TextField(16);
		addField("Author1", author1Field);
		rightColumn.add(ViewHelper.formatFieldLeft("Author", author1Field));

		fieldColumnsPanel.add(leftColumn);
		fieldColumnsPanel.add(rightColumn);

		CurrencyTextField suggestedPriceField = new CurrencyTextField(16, 16);
		suggestedPriceField.addActionListener(this);
		addField("SuggestedPrice", suggestedPriceField);
		add(ViewHelper.formatCurrencyFieldLeft("Book Value", suggestedPriceField));

		TextArea notesField = new TextArea();
		addField("Notes", notesField);
		add(ViewHelper.formatTextAreaFieldLeft("Notes", notesField));


	}

}
