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
import utilities.Key;
import utilities.DateUtil;
import exception.InvalidPrimaryKeyException;

/**
 * Book model that persists to the database.
 */
public class Book extends Model {

	public static final String TABLE_NAME = "book";
	public static final String PRIMARY_KEY = "Barcode";
	
	/** Schema for the related table */
	private static Properties schema;

	/**
	 * Constructs Book by querying db with primary key.
	 * @param id - primary key
	 * @throws InvalidPrimaryKeyException if query doesn't return 1 result
	 */
	public Book(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
	}

	/**
	 * Constructs new Book from properties object.
	 * Must specify if it is persisted.
	 * @param persistentState
	 * @param persisted
	 */
	public Book(Properties persistentState, boolean persisted) {
		super(persistentState);
	}

	/**
	 * Constructs new Book from properties object that has not been persisted yet.
	 * @param persistentState
	 */
	public Book(Properties persistentState) {
		this(persistentState, false);
	}
	@Override
	protected void setupValidations(){
		//validator.addValidation(new AlphaNumericValidation("Barcode", "Barcode"));
	}
	
	@Override
	public Properties getSchema() {
		if (schema == null) {
			schema = getSchemaInfo(TABLE_NAME);
		}
		return schema;
	}
	
	@Override
	public Object getState(String key) {
		if(key.equals(Key.GET_PERSISTENT_STATE)){
			return persistentState;
		}
		return persistentState.getProperty(key);
	}
	
	public boolean setInactive(){
		persistentState.setProperty("BookStatus", "Inactive");
		return save();
	}
	
	@Override
	public boolean beforeSave() {
		try {
			BookBarcodePrefix barcodePrefix = new BookBarcodePrefix((persistentState.getProperty(PRIMARY_KEY)).substring(0, 3));
			persistentState.setProperty("Discipline", (String)barcodePrefix.getState("Discipline"));
			persistentState.setProperty("DateOfLastUpdate", DateUtil.getDate());
		} catch (InvalidPrimaryKeyException e) {
			//TODO better error message
			System.err.println("Error saving record to database: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	@Override
	public boolean isPrimaryKeyAutoIncrement() {
		return false;
	}
}
