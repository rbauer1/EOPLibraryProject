package userinterface.view.form;

import userinterface.ViewHelper;
import userinterface.component.PasswordField;
import userinterface.component.TextField;
import userinterface.view.View;

public class LoginForm extends Form {

	private static final long serialVersionUID = -3857834050959995582L;

	public LoginForm(View view) {
		super(view);
	}

	@Override
	protected void build() {	
		TextField bannerIdField = new TextField(15);
		bannerIdField.addActionListener(this);
		addField("BannerID", bannerIdField);
		add(ViewHelper.formatFieldCenter("Banner ID", bannerIdField));
		
		PasswordField passwordField = new PasswordField(15);
		passwordField.addActionListener(this);
		addField("Password", passwordField);
		add(ViewHelper.formatFieldCenter("Password", passwordField));
	}
}
