/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package userinterface.message;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import userinterface.MainFrame;
import userinterface.component.Panel;

public class MessagePanel extends Panel {

	private static final long serialVersionUID = 7201794639172698857L;

	public MessagePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder( 5, 50, 5, 50 ));
	}

	/**
	 * Clear message
	 */
	public void clear() {
		removeAll();
		setVisible(false);
		MainFrame.getInstance().fix();
	}

	/**
	 * Display error message
	 * @param title
	 */
	public void displayErrorMessage(String title) {
		displayMessage(MessageFactory.createMessage(MessageType.ERROR, title));
	}


	/**
	 * Display error message
	 * @param title
	 * @param messages
	 */
	public void displayErrorMessage(String title, List<String> messages) {
		displayMessage(MessageFactory.createMessage(MessageType.ERROR, title, messages));
	}

	/**
	 * Displays Message
	 * @param message
	 */
	private void displayMessage(Message message) {
		removeAll();
		add(message);
		setVisible(true);
		MainFrame.getInstance().fix();
	}

	/**
	 * Display Message
	 * @param messageEvent
	 */
	public void displayMessage(MessageEvent messageEvent) {
		displayMessage(MessageFactory.createMessage(messageEvent));
	}

	/**
	 * Display Message
	 * @param type
	 * @param title
	 * @param messages
	 */
	public void displayMessage(MessageType type, String title) {
		displayMessage(MessageFactory.createMessage(type, title));
	}

	/**
	 * Display message
	 * @param type
	 * @param title
	 * @param messages
	 */
	public void displayMessage(MessageType type, String title, List<String> messages) {
		displayMessage(MessageFactory.createMessage(type, title, messages));
	}
}
