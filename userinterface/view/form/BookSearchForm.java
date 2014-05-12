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
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;

import model.BookBarcodePrefix;
import model.BookBarcodePrefixCollection;
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

	/** button that submits this form */
	private Button searchButton;

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
		leftColumn.add(ViewHelper.formatFieldLeft("Author", author1Field));

		TextField isbnField = new TextField(16);
		isbnField.addActionListener(this);
		addField("ISBN", isbnField);
		leftColumn.add(ViewHelper.formatFieldLeft("ISBN", isbnField));

		Panel rightColumn = new Panel();
		rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
		fieldColumnsPanel.add(rightColumn);

		SelectField disciplineField = new SelectField(new String[] { "Any" });
		disciplineField.addActionListener(this);
		disciplineField.setPreferredSize(new Dimension(180,25));
		addField("Discipline", disciplineField);
		rightColumn.add(ViewHelper.formatFieldLeft("Discipline", disciplineField));
		setDisciplineOptions(disciplineField);

		SelectField conditionField = new SelectField(new String[] { "Any", "Good", "Fair", "Damaged" });
		conditionField.addActionListener(this);
		conditionField.setPreferredSize(new Dimension(180,25));
		addField("Condition", conditionField);
		rightColumn.add(ViewHelper.formatFieldLeft("Condition", conditionField));

		SelectField statusField = new SelectField(new String[] { "Active", "Lost", "Inactive", "Any"});
		statusField.addActionListener(this);
		statusField.setPreferredSize(new Dimension(180,25));
		addField("Status", statusField);
		rightColumn.add(ViewHelper.formatFieldLeft("Status", statusField));

		rightColumn.add(ViewHelper.createPlaceHolder());

		searchButton = new Button("Search");
		searchButton.addActionListener(this);
		add(ViewHelper.formatCenter(searchButton));
	}

	@Override
	protected Properties filterValues(Properties values){
		if(values.getProperty("Condition", "").equals("Any")){
			values.remove("Condition");
		}
		if(values.getProperty("Status", "").equals("Any")){
			values.remove("Status");
		}
		if(values.getProperty("Discipline", "").equals("Any")){
			values.remove("Discipline");
		}
		return values;
	}

	@Override
	public void setAllFieldsEnabled(boolean enabled){
		super.setAllFieldsEnabled(enabled);
		searchButton.setEnabled(enabled);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setDisciplineOptions(final SelectField disciplineField ){
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				BookBarcodePrefixCollection prefixCollection = new BookBarcodePrefixCollection();
				prefixCollection.findAll();
				String[] disciplines = new String[prefixCollection.size() + 1];
				disciplines[0] = "Any";
				int index = 1;
				for(BookBarcodePrefix prefix : prefixCollection.getEntities()){
					disciplines[index] = (String)prefix.getState("Discipline");
					index++;
				}
				disciplineField.setModel(new DefaultComboBoxModel(disciplines));
				return null;
			}
		}.execute();
	}

}
