package userinterface.view.form;

import userinterface.ViewHelper;
import userinterface.component.PasswordField;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the input for Worker login.
 */
public class LoginForm extends Form {

	private static final long serialVersionUID = -3857834050959995582L;

	/**
	 * Creates a new Login Form
	 * @param view
	 */
	public LoginForm(View view) {
		super(view);
	}

	@Override
	protected void build() {	
		TextField bannerIdField = new TextField(16);
		bannerIdField.addActionListener(this);
		addField("BannerID", bannerIdField);
		add(ViewHelper.formatFieldStacked("Banner ID", bannerIdField));
		
		PasswordField passwordField = new PasswordField(16);
		passwordField.addActionListener(this);
		addField("Password", passwordField);
		add(ViewHelper.formatFieldStacked("Password", passwordField));
	}
}
