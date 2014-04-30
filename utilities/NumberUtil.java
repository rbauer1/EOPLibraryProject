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
	 * Formats double as a string with 2 decimal places
	 * @param number
	 * @return currencyString
	 */
	public static String formatCurrency(Double number){
		DecimalFormat formatter = new DecimalFormat("0.00");
		return formatter.format(number);
	}

	/**
	 * Formats numeric string as a string with 2 decimal places
	 * @param numberStr
	 * @return currencyString
	 */
	public static String formatCurrency(String numberStr){
		if(numberStr.matches("\\d+(.\\d+)?")) {
			return formatCurrency(Double.parseDouble(numberStr));
		}
		return "";
	}

	/**
	 * Private constructor to prevent instantiation
	 */
	private NumberUtil(){}

}
