package userinterface.view.form;

import java.awt.Dimension;

import javax.swing.BoxLayout;

import userinterface.component.Label;
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
		printerField.setPreferredSize(new Dimension(200, 25));
		printerField.addActionListener(this);
		addField("Printer", printerField);
		Label label = new Label("Printer");
		label.setPreferredSize(new Dimension(80, 25));
		add(label);
		add(printerField);

		NumericTextField copiesField = new NumericTextField(4, 4);
		copiesField.setMaximumSize(new Dimension(50, 25));
		copiesField.addActionListener(this);
		addField("Copies", copiesField);
		label = new Label("   Copies");
		label.setPreferredSize(new Dimension(80, 25));
		add(label);
		add(copiesField);
	}
}
