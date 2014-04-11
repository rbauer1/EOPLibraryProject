package userinterface.view.form;

import userinterface.ViewHelper;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the a banner id as input
 */
public class BannerIdForm extends Form {

	private static final long serialVersionUID = -3857834050959995582L;

	/**
	 * Creates a new Banner Id Form
	 * @param view
	 */
	public BannerIdForm(View view) {
		super(view);
	}

	@Override
	protected void build() {	
		TextField bannerIdField = new TextField(15);
		bannerIdField.addActionListener(this);
		addField("BannerID", bannerIdField);
		add(ViewHelper.formatFieldCenter("Banner ID", bannerIdField));
	}
}
