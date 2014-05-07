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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import userinterface.Config;
import userinterface.MainFrame;
import userinterface.ViewHelper;
import model.Worker;
import userinterface.component.Panel;
import userinterface.component.flat.FButton;
import userinterface.component.flat.Icons;
import userinterface.menu.MButton;
import userinterface.message.MessageEvent;
import userinterface.utilities.Utils;
import utilities.Key;
import controller.Controller;

/**
 * Main Menu Screen. Serves as the main transaction selection screen.
 */
public class MainMenuView extends View {

	private static final long serialVersionUID = -4462137345508528750L;

	/* Buttons */
	private JButton bookActionsButton;
	private JButton borrowerActionsButton;
	private JButton checkinBookButton;
	private JButton checkoutBookButton;
	private JButton logoutButton;
	private JButton workerActionsButton;

	/**
	 * Constructs main menu view
	 * @param controller
	 */
	public MainMenuView(Controller controller) {
		super(controller, "Main Menu");
		subscribeToController(Key.MESSAGE);
	}


	@Override
	public void afterShown(){
		messagePanel.clear();
	}
	
	

	@Override
	protected void build() {
	}

	@Override
	public void processAction(Object source) {
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}

}
