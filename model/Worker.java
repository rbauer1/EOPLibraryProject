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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;

/**
 * Worker model that persists to the database.
 */
public class Worker extends EntityBase {

	public static final String TABLE_NAME = "worker";
	public static final String PRIMARY_KEY = "BannerID";
	public static final String PASSWORD_SALT = "68352016baee847f64eb6c2a35eeea67";
	private static Properties schema;
	private boolean persisted;

	public Worker(String id) throws InvalidPrimaryKeyException {
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

	public Worker(Properties persistentState, boolean persisted) {
		super(TABLE_NAME);
		this.persisted = persisted;
		setPersistentState(persistentState);
	}

	public Worker(Properties persistentState) {
		this(persistentState, false);
	}

	public Object getState(String key) {
		return this.persistentState.getProperty(key);
	}

	public void stateChangeRequest(String key, Object value) {
		this.persistentState.setProperty(key, (String) value);
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
		whereClause.setProperty(PRIMARY_KEY,
				persistentState.getProperty(PRIMARY_KEY));
		updatePersistentState(schema, persistentState, whereClause);
	}

	private void setPersistentState(Properties state) {
		this.persistentState = new Properties();
		Enumeration<?> keys = state.propertyNames();
		while (keys.hasMoreElements() == true) {
			String key = (String) keys.nextElement();
			String value = state.getProperty(key);

			if (value != null && key != null) {
				this.persistentState.setProperty(key, value);
			}
		}
	}
	
	public boolean validPassword(String password) {
		return persistentState.get("Password").equals(encryptPassword(password));
	}


	@Override
	protected void initializeSchema(String tableName) {
		if (schema == null) {
			schema = getSchemaInfo(tableName);
		}
	}

	private static String encryptPassword(String password) {
		try{
			// Create MessageDigest instance for MD5
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        //Add salt to digest
	        md.update(PASSWORD_SALT.getBytes());
	        //Add password to digest
	        byte[] bytes = md.digest(password.getBytes());
	        //This bytes[] has bytes in decimal format;
	        //Convert it to hexadecimal format
	        StringBuilder sb = new StringBuilder();
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
			return sb.toString().substring(0, 20);
		} catch (NoSuchAlgorithmException e) {
			return password;
		}
	}

}
