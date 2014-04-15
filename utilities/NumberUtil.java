/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package utilities;

import java.text.DecimalFormat;


/**
 * Class that holds various number helper methods
 */
public class NumberUtil {
	
	/**
	 * Private constructor to prevent instantiation
	 */
	private NumberUtil(){}

	
	/**
	 * Does not prepend with $
	 * @param number
	 * @return
	 */
	public static String getAsCurrencyString(Object number){
		DecimalFormat formatter = new DecimalFormat("#.00");
		return formatter.format(number);
	}
	
}
