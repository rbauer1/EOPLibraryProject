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
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import userinterface.view.form.Form;

/**
 * Text Field to be put in a Form.
 */
public class TextField extends JTextField implements FormField {

	private static final long serialVersionUID = -4065356440835532294L;
	
	/** Border Color of a Field **/
	private static final Color BORDER_COLOR = Form.FIELD_BORDER_COLOR;
	
	/** Font of a Field **/
	private static final Font FONT = Form.NORMAL_FONT;
	
	/** Size of Field */
	private static final Dimension SIZE = new Dimension(185, 25);

	public TextField(int columns) {
		super(columns);
		setFont(FONT);
		setAlignmentX(CENTER_ALIGNMENT);
		setBorder(new LineBorder(BORDER_COLOR, 1));
		setPreferredSize(SIZE);
		setMaximumSize(SIZE);
	}

	@Override
	public String getValue() {
		return getText().trim();
	}
	
	@Override
	public void setValue(String value) {
		setText(value);
	}
	
	@Override
	public void reset() {
		setText("");
	}
}
