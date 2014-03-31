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

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import utilities.Key;

import exception.InvalidPrimaryKeyException;

/**
 * Worker model that persists to the database.
 */
public class Book extends EntityBase {

	public static final String TABLE_NAME = "book";
	public static final String PRIMARY_KEY = "Barcode";
	private static Properties schema;
	private boolean persisted;
	private static SecureRandom random;
	private String resetToken;

	public Book(String id) throws InvalidPrimaryKeyException {
		super(TABLE_NAME);
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRIMARY_KEY
				+ " = '" + id + "'";
		List<?> result = getSelectQueryResult(query);
		if (result == null || result.size() != 1) {
			throw new InvalidPrimaryKeyException("Invalid primary key value: " + id);
		}
		this.persisted = true;
		setPersistentState((Properties) result.get(0));
	}

	public Book(Properties persistentState, boolean persisted) {
		super(TABLE_NAME);
		this.persisted = persisted;
		setPersistentState(persistentState);
	}

	public Book(Properties persistentState) {
		this(persistentState, false);
	}

	public Object getState(String key) {
		return this.persistentState.getProperty(key);
	}

	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SUBMIT_NEW_BOOK)){
			persistentState = (Properties)value;
		}
	}

	public boolean save() {
		try {
			String key = (String) this.getState(PRIMARY_KEY);
			if (this.persisted && key != null && key != "") {
				update();
			} else {
				insert();
			}
		} catch (SQLException e) {
			System.err.println("Error saving record to database: "
					+ e.getMessage());
			return false;
		}
		this.persisted = true;
		return true;
	}

	private void insert() throws SQLException {
		insertPersistentState(schema, persistentState);
	}

	private void update() throws SQLException {
		Properties whereClause = new Properties();
		whereClause.setProperty(PRIMARY_KEY, persistentState.getProperty(PRIMARY_KEY));
		updatePersistentState(schema, persistentState, whereClause);
	}

	private void setPersistentState(Properties state) {
		this.persistentState = new Properties();
		Enumeration<?> keys = state.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = state.getProperty(key);

			if (value != null && key != null) {
				this.persistentState.setProperty(key, value);
			}
		}
	}
	
	
	public String getResetToken() {
		  if(random == null){
			  random = new SecureRandom();
		  }
		  resetToken = new BigInteger(130, random).toString(32);
		  return resetToken;
	}

	@Override
	protected void initializeSchema(String tableName) {
		if (schema == null) {
			schema = getSchemaInfo(tableName);
		}
	}

}
