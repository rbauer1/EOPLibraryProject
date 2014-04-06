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
 * Message Box for displaying info 
 */
public class InfoMessage extends Message {

	private static final long serialVersionUID = -4606677913954854280L;

	/** Color of Font */
	private static final Color FONT_COLOR = new Color(0x3a87ad);
	
	/** Color of Background */
	private static final Color BACKGROUND_COLOR = new Color(0xC3D6E0);
	
	/** Color of Border */
	private static final Color BORDER_COLOR = new Color(0x9CA9AF);

	/**
	 * Construct info message
	 * @param title
	 * @param messages
	 */
	public InfoMessage(String title, List<String> messages) {
		super(title, messages);
	}

	/**
	 * Construct info message
	 * @param title
	 */
	public InfoMessage(String title) {
		super(title);
	}
	
	protected void format(){
		label.setForeground(FONT_COLOR);
		setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		setBackground(BACKGROUND_COLOR);
	}
}

