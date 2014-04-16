package userinterface.view.form;

import userinterface.ViewHelper;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the a barcode as input
 */
public class BarcodeForm extends Form {

	private static final long serialVersionUID = -3857834050959995582L;

	/**
	 * Creates a new Barcode Form
	 * @param view
	 */
	public BarcodeForm(View view) {
		super(view);
	}

	@Override
	protected void build() {
		TextField barcodeField = new TextField(16);
		barcodeField.addActionListener(this);
		addField("Barcode", barcodeField);
		add(ViewHelper.formatFieldCenter("Barcode", barcodeField));
	}
}
