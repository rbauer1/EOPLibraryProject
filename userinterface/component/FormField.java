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


/**
 * Interface for a field that can be part of a Form
 */
public interface FormField {
	
	/**
	 * Returns value of field.
	 * @return
	 */
	public String getValue();
	
	/**
	 * Sets value of field.
	 * @return
	 */
	public void setValue(String value);
	
	/**
	 * Reset field to default value.
	 */
	public void reset();
	
	public boolean requestFocusInWindow();
	
}
