/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.component;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

/**
 * Basic Panel of the default application color
 */
public class Panel extends JPanel {
	
	/** Background Color of the panel */
	public static final Color BACKGROUND_COLOR = new Color(0xF0F0E8);
	
	private static final long serialVersionUID = 3274303070952112811L;
	
	public Panel() {
		setBackground(BACKGROUND_COLOR);
	}
	
	public Panel(LayoutManager lm){
		super(lm);
		setBackground(BACKGROUND_COLOR);
	}
}
