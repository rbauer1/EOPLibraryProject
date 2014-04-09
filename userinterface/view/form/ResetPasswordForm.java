package userinterface.view.form;

import userinterface.ViewHelper;
import userinterface.component.PasswordField;
import userinterface.component.TextField;
import userinterface.view.View;

/**
 * Form that takes the info for a password reset
 */
public class ResetPasswordForm extends Form {

	private static final long serialVersionUID = -3857834050959995582L;

	/**
	 * Creates a new Reset Password Form
	 * @param view
	 */
	public ResetPasswordForm(View view) {
		super(view);
	}

	@Override
	protected void build() {	
		TextField resetCodeField = new TextField(15);
		resetCodeField.addActionListener(this);
		addField("ResetCode", resetCodeField);
		add(ViewHelper.formatFieldCenter("Reset Code", resetCodeField));
		
		PasswordField passwordField = new PasswordField(16);
		passwordField.addActionListener(this);
		addField("NewPassword", passwordField);
		add(ViewHelper.formatFieldLeft("New Password", passwordField));
		
		PasswordField confirmPasswordField = new PasswordField(16);
		confirmPasswordField.addActionListener(this);
		addField("NewPasswordConfirmation", confirmPasswordField);
		add(ViewHelper.formatFieldLeft("Confirm Password", confirmPasswordField));
	}
}
