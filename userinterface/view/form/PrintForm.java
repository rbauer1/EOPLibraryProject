package userinterface.view.form;

import javax.swing.BoxLayout;

import userinterface.ViewHelper;
import userinterface.component.NumericTextField;
import userinterface.component.SelectField;
import userinterface.view.PrintPDFView;

/**
 * Form that takes the input for printing a document
 */
public class PrintForm extends Form {

	private static final long serialVersionUID = -3857834050959995582L;

	/**
	 * Creates a Print Form
	 * @param view
	 */
	public PrintForm(PrintPDFView view) {
		super(view);
	}

	@Override
	protected void build() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		SelectField printerField = new SelectField(((PrintPDFView)view).getPrinters());
		printerField.addActionListener(this);
		addField("Printer", printerField);
		add(ViewHelper.formatFieldLeft("Printer", printerField));

		NumericTextField copiesField = new NumericTextField(16, 4);
		copiesField.addActionListener(this);
		copiesField.setValue("1");
		addField("Copies", copiesField);
		add(ViewHelper.formatFieldLeft("Copies", copiesField));
	}
}
