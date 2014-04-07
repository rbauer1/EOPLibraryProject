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

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;

import utilities.DateUtil;

/**
 * Date Field for use within a Form
 */
public class DateField extends Panel implements FormField {

	private static final long serialVersionUID = -1652629883738808974L;

	private SelectField monthField;
	private SelectField dayField;
	private SelectField yearField;
	
	private Calendar calendar;		
	
	/**
	 * Constructs a date field.
	 */
	public DateField() {
		super(new FlowLayout(FlowLayout.LEFT));
		calendar = new GregorianCalendar();
		build();
		addListeners();
	}

	@Override
	public String getValue() {
		return DateUtil.getDate(calendar.getTime());
	}

	@Override
	public void setValue(String value) {
		calendar.setTime(DateUtil.getDate(value));
		build();
	}

	@Override
	public void reset() {
		calendar.setTime(new Date());
		build();
	}
	
	@Override
	public boolean requestFocusInWindow(){
		return monthField.requestFocusInWindow();
	}
	
	/**
	 * Builds the field
	 */
	private void build(){
		this.removeAll();
		
		monthField = new SelectField(DateUtil.getMonths());
		monthField.setValue(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US));
		add(monthField);
		
		dayField = new SelectField(new String[] {});
		buildDayField();
		add(dayField);
		
		int year = calendar.get(Calendar.YEAR);
		String[] years = new String[100];
		for(int i = year - 75, j = 0; i < year + 25; i++, j++){
			years[j] = i + "";
		}
		yearField = new SelectField(years);
		yearField.setValue(calendar.get(Calendar.YEAR) + "");
		add(yearField);
	}
	
	/**
	 * Builds the data for the day of month selection.
	 * Called when month or year changes and number of days in month changes also
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildDayField() {
		int numDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] days = new String[numDays];
		for(int i = 1; i <= numDays; i++){
			days[i - 1] = (i < 10 ? "0" : "") + i;
		}
		dayField.setModel(new DefaultComboBoxModel(days));
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		dayField.setValue((day < 10 ? "0" : "") + day);
	}
	
	/**
	 * Adds listeners to the select boxes
	 */
	private void addListeners(){
		monthField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int currentNumDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				int year = calendar.get(Calendar.YEAR);
				int month = DateUtil.getMonth(monthField.getValue());
				calendar.set(Calendar.MONTH, month);
				int newNumDays = DateUtil.getNumberOfDays(year, month);
				if (currentNumDays != newNumDays) {
					buildDayField();
				}
			}
			
		});
		
		dayField.addActionListener (new ActionListener () {
			
		    public void actionPerformed(ActionEvent e) {
		        int dayOfMonth = Integer.parseInt(dayField.getValue());
		        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		    }
		    
		});
		
		yearField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				int currentNumDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
				int month = DateUtil.getMonth(monthField.getValue());
				int year = Integer.parseInt(yearField.getValue());
		        calendar.set(Calendar.YEAR, year);
		        int newNumDays = DateUtil.getNumberOfDays(year, month);
				if (currentNumDays != newNumDays) {
					buildDayField();
				}
			}
			
		});
	}

}
