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

import userinterface.view.View;

/**
 * Message Box for displaying errors
 */
public class BlankMessage extends Message {

	private static final long serialVersionUID = -1851406292991272965L;

	/** Color of Font */
	private static final Color FONT_COLOR = new Color(0xb94a48);
	
	/** Color of Background */
	private static final Color BACKGROUND_COLOR = View.BACKGROUND_COLOR;
	
	/** Color of Border */
	private static final Color BORDER_COLOR = View.BACKGROUND_COLOR;

	/**
	 * Construct error message
	 * @param title
	 * @param messages
	 */
	public BlankMessage(String title, List<String> messages) {
		super(title, messages);
	}

	/**
	 * Construct error message
	 * @param title
	 */
	public BlankMessage(String title) {
		super(title);
	}
	
	protected void format(){
		label.setForeground(FONT_COLOR);
		setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
		setBackground(BACKGROUND_COLOR);
	}
}
