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

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Class that holds various date helper methods
 */
public class DateUtil {
	
	/** Standard format for which dates are stored */
	private static final DateFormat DATE = new SimpleDateFormat("yyyy-MM-dd");
	
	/** Standard format for which dates and times are stored */
	private static final DateFormat DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * Private constructor to prevent instantiation
	 */
	private DateUtil(){}
		
	/**
	 * Returns today's date as a string
	 * @return today's date string
	 */
	public static String getDate(){
		return getDate(new Date());
	}
	
	/**
	 * Returns current date and time as a string
	 * @return current date and time string
	 */
	public static String getDateTime(){
		return getDateTime(new Date());
	}
	
	/**
	 * Returns provided date as default format
	 * @param date
	 * @return date string
	 */
	public static String getDate(Date date){
		return DATE.format(date);
	}
	
	/**
	 * Creates date object from date string
	 * @param dateStr
	 * @return date
	 */
	public static Date getDate(String dateStr){
		try {
			return DATE.parse(dateStr);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date string provided");
		}
	}
	
	/**
	 * Returns provided date as default format
	 * @param date
	 * @return date string
	 */
	public static String getDateTime(Date date){
		return DATE_TIME.format(date);
	}
	
	
	/**
	 * Returns all months in a string array.
	 * Indexes start from 0. i.e. 0 -> January
	 * @return months
	 */
	public static String[] getMonths(){
		return new DateFormatSymbols().getMonths();
	}
	
	/**
	 * Returns the index of month name provided.
	 * Indexes start from 0. i.e. January -> 0
	 * @param monthName
	 * @return index of month
	 */
	public static int getMonth(String monthName){
		String[] months = getMonths();
        int month = 0;
        for(int i = 0; i < months.length; i++){
        	if(monthName.equals(months[i])){
        		month = i;
        	}
        }
        return month;
	}
	
	/**
	 * Returns the number of days in the provided month and year.
	 * @param year
	 * @param month
	 * @return number of days in month
	 */
	public static int getNumberOfDays(int year, int month){
		Calendar calendar = new GregorianCalendar(year, month, 1);
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
	}

}
