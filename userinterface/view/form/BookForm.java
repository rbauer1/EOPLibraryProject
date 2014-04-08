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
import userinterface.component.SelectField;
import userinterface.component.TextArea;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the input for a Book model.
 * Can be used for adding of modifying a Book.
 */
public class BookForm extends Form {

	private static final long serialVersionUID = 8891254032102434181L;

	/**
	 * Constructs new Book Form
	 * @param view
	 */
	public BookForm(View view) {
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

		TextField barcodeField = new TextField(16);
		barcodeField.addActionListener(this);
		addField("Barcode", barcodeField);
		leftColumn.add(ViewHelper.formatFieldLeft("Barcode", barcodeField));
		
		TextField titleField = new TextField(16);
		titleField.addActionListener(this);
		addField("Title", titleField);
		leftColumn.add(ViewHelper.formatFieldLeft("Title", titleField));
		
		TextField author1Field = new TextField(16);
		author1Field.addActionListener(this);
		addField("Author1", author1Field);
		leftColumn.add(ViewHelper.formatFieldLeft("Author 1", author1Field));

		TextField author2Field = new TextField(16);
		author2Field.addActionListener(this);
		addField("Author2", author2Field);
		leftColumn.add(ViewHelper.formatFieldLeft("Author 2", author2Field));
		
		TextField isbnField = new TextField(16);
		isbnField.addActionListener(this);
		addField("ISBN", isbnField);
		leftColumn.add(ViewHelper.formatFieldLeft("ISBN", isbnField));
		
		Panel rightColumn = new Panel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(rightColumn);
		
		TextField publisherField = new TextField(16);
		publisherField.addActionListener(this);
		addField("Publisher", publisherField);
		rightColumn.add(ViewHelper.formatFieldLeft("Publisher", publisherField));
		
		NumericTextField publicationYearField = new NumericTextField(16, 4);
		publicationYearField.addActionListener(this);
		addField("YearOfPublication", publicationYearField);
		rightColumn.add(ViewHelper.formatFieldLeft("Publication Year", publicationYearField));
		
		SelectField conditionField = new SelectField(new String[] { "Good", "Damaged" });
		addField("Condition", conditionField);
		rightColumn.add(ViewHelper.formatFieldLeft("Condition", conditionField));
		
		CurrencyTextField suggestedPriceField = new CurrencyTextField(16, 16);
		suggestedPriceField.addActionListener(this);
		addField("SuggestedPrice", suggestedPriceField);
		rightColumn.add(ViewHelper.formatCurrencyFieldLeft("Suggested Price", suggestedPriceField));	
		
		rightColumn.add(ViewHelper.createPlaceHolder());
		
		TextArea notesField = new TextArea();
		addField("Notes", notesField);
		add(ViewHelper.formatTextAreaFieldLeft("Notes", notesField));	
	}

}
