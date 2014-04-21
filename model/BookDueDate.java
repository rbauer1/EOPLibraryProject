/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package model;

import java.util.Properties;

import model.validation.DateValidation;
import model.validation.PresenceValidation;
import event.Event;
import exception.InvalidPrimaryKeyException;

/**
 * Book Due Date model that persists to the database.
 */
public class BookDueDate extends Model {

	public static final String PRIMARY_KEY = "CurrentMaxDueDate";

	/** Schema for the related table */
	private static Properties schema;

	public static final String TABLE_NAME = "maxduedate";

	public BookDueDate() {
		super(true);
		try {
			findByQuery("SELECT * FROM " + TABLE_NAME + " LIMIT 0,1;");
		} catch (InvalidPrimaryKeyException e) {
			new Event(Event.getLeafLevelClassName(this), "init",
					"SQL error while fetching date: " + e.toString(), Event.ERROR);
		}
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	@Override
	public Properties getSchema() {
		if (schema == null) {
			schema = new Properties();
			schema.setProperty(PRIMARY_KEY, "text");
		}
		return schema;
	}

	@Override
	public Object getState(String key) {
		if(key.equals("DueDate")){
			key = PRIMARY_KEY; //Alias primary key as DueDate
		}
		return super.getState(key);
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean isPrimaryKeyAutoIncrement() {
		return false;
	}

	@Override
	protected void setupValidations(){
		validator.addValidation(new PresenceValidation("DueDate", "Due Date"));
		validator.addValidation(new DateValidation("DueDate", "Due Date"));
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals("DueDate")){
			key = PRIMARY_KEY; //Alias primary key as DueDate
		}
		super.stateChangeRequest(key, value);
	}
}
