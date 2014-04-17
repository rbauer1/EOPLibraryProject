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

import java.awt.Color;
import java.util.List;

import javax.swing.BorderFactory;

/**
 * Message Box for displaying success messages
 */
public class SuccessMessage extends Message {

	private static final long serialVersionUID = -4606677913954854280L;

	/** Color of Font */
	private static final Color FONT_COLOR = new Color(0x468847);
	
	/** Color of Background */
	private static final Color BACKGROUND_COLOR = new Color(0xdff0d8);
	
	/** Color of Border */
	private static final Color BORDER_COLOR = new Color(0xd6e9c6);

	/**
	 * Construct success message
	 * @param title
	 * @param messages
	 */
	public SuccessMessage(String title, List<String> messages) {
		super(title, messages);
	}

	/**
	 * Construct success message
	 * @param title
	 */
	public SuccessMessage(String title) {
		super(title);
	}
	
	protected void format(){
		label.setForeground(FONT_COLOR);
		setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		setBackground(BACKGROUND_COLOR);
	}
}
