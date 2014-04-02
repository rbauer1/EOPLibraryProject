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

import exception.InvalidPrimaryKeyException;

/**
 * Book model that persists to the database.
 */
public class BookBarcodePrefix extends Model {

	public static final String TABLE_NAME = "bookbarcodeprefix";
	public static final String PRIMARY_KEY = "PrefixValue";

	/** Schema for the related table */
	private static Properties schema;

	/**
	 * Constructs BookBarcodePrefix by querying db with primary key.
	 * @param id - primary key
	 * @throws InvalidPrimaryKeyException if query doesn't return 1 result
	 */
	public BookBarcodePrefix(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
	}

	/**
	 * Constructs new BookBarcodePrefix from properties object.
	 * Must specify if it is persisted.
	 * @param persistentState
	 * @param persisted
	 */
	public BookBarcodePrefix(Properties persistentState, boolean persisted) {
		super(persistentState, persisted);
	}

	/**
	 * Constructs new BookBarcodePrefix from properties object that has not been persisted yet.
	 * @param persistentState
	 */
	public BookBarcodePrefix(Properties persistentState) {
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
