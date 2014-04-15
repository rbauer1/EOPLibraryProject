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
import model.validation.InclusionValidation;
import model.validation.LengthValidation;
import model.validation.NumericValidation;
import model.validation.PresenceValidation;
import utilities.DateUtil;
import utilities.Key;
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
		super(persistentState, persisted);
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
		validator.addValidation(new PresenceValidation("Barcode", "Barcode"));
		validator.addValidation(new LengthValidation("Barcode", "Barcode", 4, 10));
		
		validator.addValidation(new PresenceValidation("Title", "Title"));
		validator.addValidation(new LengthValidation("Title", "Title", 1, 50));
		
		validator.addValidation(new PresenceValidation("Discipline", "Discipline", "is invalid, verify barcode is correct"));
		validator.addValidation(new LengthValidation("Discipline", "Discipline", 1, 25));
		
		validator.addValidation(new PresenceValidation("Author1", "Author 1"));
		validator.addValidation(new LengthValidation("Author1", "Author 1", 1, 30));
		
		validator.addValidation(new LengthValidation("Author2", "Author 2", 0, 30));
		validator.addValidation(new LengthValidation("Author3", "Author 3", 0, 30));
		validator.addValidation(new LengthValidation("Author4", "Author 4", 0, 30));
		
		validator.addValidation(new PresenceValidation("Publisher", "Publisher"));
		validator.addValidation(new LengthValidation("Publisher", "Publisher", 1, 30));
		
		validator.addValidation(new NumericValidation("YearOfPublication", "Publication Year", true));
		
		validator.addValidation(new LengthValidation("ISBN", "ISBN", 0, 15));

		validator.addValidation(new InclusionValidation("Condition", "Condition", new String[] {"Good", "Fair", "Damaged"}));
	
		validator.addValidation(new PresenceValidation("SuggestedPrice", "Suggested Price"));
		NumericValidation priceValidation = new NumericValidation("SuggestedPrice", "Suggested Price");
		priceValidation.requireGreaterThanOrEqualTo(0);
		validator.addValidation(priceValidation);
		
		validator.addValidation(new LengthValidation("Notes", "Notes", 0, 300));
		
		validator.addValidation(new InclusionValidation("Status", "Status", new String[] {"Active", "Lost", "Inactive"}));

		validator.addValidation(new PresenceValidation("DateOfLastUpdate", "Date of Last Update"));
		validator.addValidation(new DateValidation("DateOfLastUpdate", "Date of Last Update"));
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
		return super.getState(key);
	}
	
	public boolean setInactive(String notes){
		persistentState.setProperty("Notes", notes);
		persistentState.setProperty("Status", "Inactive");
		return save();
	}
	
	public boolean setLost(String notes){
		persistentState.setProperty("Notes", notes);
		persistentState.setProperty("Status", "Lost");
		return save();
	}
	
	@Override
	public boolean beforeValidate(boolean isCreate) {
		if(isCreate){
			persistentState.setProperty("Status", "Active");
		}
		
		persistentState.setProperty("DateOfLastUpdate", DateUtil.getDate());
		
		if(persistentState.getProperty("Barcode", "").length() > 3){
			try {
				BookBarcodePrefix barcodePrefix = new BookBarcodePrefix((persistentState.getProperty(PRIMARY_KEY)).substring(0, 3));
				persistentState.setProperty("Discipline", (String)barcodePrefix.getState("Discipline"));
			} catch (InvalidPrimaryKeyException e) {
				return false;
			}		
		}
		
		return true;
	}
	
	@Override
	public boolean beforeSave(boolean isCreate) {

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
