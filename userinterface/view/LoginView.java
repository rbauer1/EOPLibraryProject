/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.view;

import java.awt.FlowLayout;
import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import userinterface.component.PasswordField;
import userinterface.component.TextField;
import userinterface.view.form.Form;
import utilities.Key;
import controller.Controller;

/** 
 * The Login View for the EOP Library application.
 * Provides the interface for the workers to login to the system. 
 */
public class LoginView extends View {
	
	private static final long serialVersionUID = 5785171554484853130L;

	/* Buttons */
	private Button loginButton;
	private Button forgotPasswordButton;
	private Button exitButton;

	/** Form to take in worker login data */
	private LoginForm form;
	
	/**
	 * Constructs Login view object and subscribes to the Librarian model.
	 * @param model The Librarian
	 */
	public LoginView(Controller controller) {
		super(controller, "Login");
		
		// Create Login form
		form = new LoginForm(this);
		add(form);
		
		// Create Button Panel
		add(createButtonsPanel());	
		
		// Subscribe the to model events
		controller.subscribe(Key.LOGIN_ERROR, this);
	}	

	/**
	 * Create button panel for this view.
	 * @return button panel
	 */
	private JPanel createButtonsPanel() {
		JPanel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));

		loginButton = new Button("Login");
		loginButton.addActionListener(this);
		buttonPanel.add(loginButton);

		buttonPanel.add(new JLabel("     "));
		
		forgotPasswordButton = new Button("Forgot Password");
		forgotPasswordButton.addActionListener(this);
		buttonPanel.add(forgotPasswordButton);

		buttonPanel.add(new JLabel("     "));

		exitButton = new Button("Exit");
		exitButton.addActionListener(this);
		buttonPanel.add(exitButton);

		return ViewHelper.formatCenter(buttonPanel);
	}

	/**
	 * Called when click or focus lost event occurs on GUI component.
	 * Used to handle button clicks to submit forms or to handle navigation.
	 */
	public void processAction(EventObject event) {
		messagePanel.clear();
		Object source = event.getSource();
		
		if (source == exitButton) {
			controller.stateChangeRequest(Key.EXIT_SYSTEM, null);
		} else if (source == forgotPasswordButton) {
			controller.stateChangeRequest(Key.EXECUTE_RECOVER_PW, null);
		} else if (source == loginButton || source == form) {	
			controller.stateChangeRequest(Key.LOGIN, form.getValues());
		}

	}
	
	/**
	 * Called when events this View subscribed to is changed.
	 * Should be used to update what is displayed in the view.
	 */
	public void updateState(String key, Object value) {
		if (key.equals(Key.LOGIN_ERROR)) {
			messagePanel.displayErrorMessage(value.toString());
		}

	}
	
	/**
	 * Form that takes worker login information
	 */
	private class LoginForm extends Form{

		private static final long serialVersionUID = -3857834050959995582L;

		public LoginForm(View view) {
			super(view);
		}

		@Override
		protected void build() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			TextField bannerIdField = new TextField(15);
			bannerIdField.addActionListener(this);
			addField("BannerID", bannerIdField);
			add(ViewHelper.formatFieldCenter("Username", bannerIdField));
			
			PasswordField passwordField = new PasswordField(15);
			passwordField.addActionListener(this);
			addField("Password", passwordField);
			add(ViewHelper.formatFieldCenter("Password", passwordField));
		}
		
	}

}
