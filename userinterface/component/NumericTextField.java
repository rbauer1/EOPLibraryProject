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

import java.awt.Toolkit;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 * Text Field that only allows a numeric input
 */
public class NumericTextField extends TextField {
	
	private static final long serialVersionUID = 552638981505544789L;
	
	private int limit;

	/**
	 * Construct Numeric TextField
	 * @param columns
	 * @param limit - max length
	 */
	public NumericTextField(int columns, int limit) {
		super(columns);
		this.limit = limit;
	}
	
	/**
	 * Construct Numeric TextField with no length limit
	 * @param columns
	 */
	public NumericTextField(int columns) {
		this(columns, Integer.MAX_VALUE);
	}

	protected Document createDefaultModel() {
		return new NumericDocument();
	}

	private class NumericDocument extends PlainDocument {

		private static final long serialVersionUID = -2274035188405696350L;
		
		private final Pattern DIGITS = Pattern.compile("\\d*\\.?\\d*");

		public void insertString(int offset, String str, AttributeSet a)
				throws BadLocationException {
			/*
			 * If the string is not null, and if it matches the regex defined
			 * above, insert the string.
			 */
			if ((str != null && DIGITS.matcher(str).matches())
					&& ((getLength() + str.length()) <= limit)) {
				super.insertString(offset, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}