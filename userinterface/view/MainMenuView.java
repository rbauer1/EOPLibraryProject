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

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import userinterface.component.Panel;
import userinterface.message.MessageEvent;
import userinterface.message.MessagePanel;
import utilities.Key;
import controller.Controller;

/**
 * Main Menu Screen. Serves as the main transaction selection screen.
 */
public class MainMenuView extends View {
	private static final long serialVersionUID = -4462137345508528750L;

	/**
	 * Constructs main menu view
	 * @param controller
	 */
	public MainMenuView(Controller controller) {
		super(controller, "");
		subscribeToController(Key.MESSAGE);
	}


	@Override
	public void afterShown(){
		messagePanel.clear();
	}



	@Override
	protected void build() {
		setLayout(new FlowLayout(FlowLayout.CENTER));
		remove(messagePanel);
		Panel mainPanel = new Panel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel(new ImageIcon("assets/images/MainScreenLogo.png")));
		messagePanel = new MessagePanel();
		mainPanel.add(messagePanel);
		add(mainPanel);
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
