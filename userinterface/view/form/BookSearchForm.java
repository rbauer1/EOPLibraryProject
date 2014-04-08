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

import java.util.Properties;

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import userinterface.component.SelectField;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Search Form to input search data for books
 */
public class BookSearchForm extends Form {

	private static final long serialVersionUID = -6075045845780411636L;

	/**
	 * Constructs new Book Search Form
	 * @param view
	 */
	public BookSearchForm(View view) {
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
		
		Panel rightColumn = new Panel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(rightColumn);
		
		TextField isbnField = new TextField(16);
		isbnField.addActionListener(this);
		addField("ISBN", isbnField);
		rightColumn.add(ViewHelper.formatFieldLeft("ISBN", isbnField));
		
		SelectField conditionField = new SelectField(new String[] { "Active", "Lost", "Inactive", "Any"});
		conditionField.addActionListener(this);
		addField("Status", conditionField);
		rightColumn.add(ViewHelper.formatFieldLeft("Status", conditionField));
		
		rightColumn.add(ViewHelper.createPlaceHolder());
		
		Button searchButton = new Button("Search");
		searchButton.addActionListener(this);
		add(ViewHelper.formatCenter(searchButton));
	}
	
	@Override
	public Properties getValues(){
		Properties values = super.getValues();
		if(values.getProperty("Status", "").equals("Any")){
			values.remove("Status");
		}
		return values;		
	}

}
