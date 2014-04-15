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

import model.validation.AlphaNumericValidation;
import model.validation.BannerIdValidation;
import model.validation.DateValidation;
import model.validation.EmailValidation;
import model.validation.InclusionValidation;
import model.validation.LengthValidation;
import model.validation.NumericValidation;
import model.validation.PhoneValidation;
import model.validation.PresenceValidation;
import utilities.DateUtil;
import utilities.NumberUtil;
import exception.InvalidPrimaryKeyException;

/**
 * Borrower model that persists to the database.
 */
public class Borrower extends Model {

	public static final String TABLE_NAME = "studentborrower";
	public static final String PRIMARY_KEY = "BannerID";
	
	/** Schema for the related table */
	private static Properties schema;

	/**
	 * Constructs Borrower by querying db with primary key.
	 * @param id - primary key
	 * @throws InvalidPrimaryKeyException if query doesn't return 1 result
	 */
	public Borrower(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
	}

	/**
	 * Constructs new Borrower from properties object.
	 * Must specify if it is persisted.
	 * @param persistentState
	 * @param persisted
	 */
	public Borrower(Properties persistentState, boolean persisted) {
		super(persistentState, persisted);
	}

	/**
	 * Constructs new Borrower from properties object that has not been persisted yet.
	 * @param persistentState
	 */
	public Borrower(Properties persistentState) {
		this(persistentState, false);
	}
	
	@Override
	protected void setupValidations(){
		validator.addValidation(new PresenceValidation("BannerID", "Banner Id"));
		validator.addValidation(new BannerIdValidation("BannerID", "Banner Id"));
		
		validator.addValidation(new PresenceValidation("FirstName", "First Name"));
		validator.addValidation(new AlphaNumericValidation("FirstName", "First Name"));
		
		validator.addValidation(new PresenceValidation("LastName", "Last Name"));
		validator.addValidation(new AlphaNumericValidation("LastName", "Last Name"));
		
		validator.addValidation(new PresenceValidation("ContactPhone", "Phone"));
		validator.addValidation(new PhoneValidation("ContactPhone", "Phone"));
		
		validator.addValidation(new PresenceValidation("Email", "Email"));
		validator.addValidation(new EmailValidation("Email", "Email"));
		
		validator.addValidation(new InclusionValidation("BorrowerStatus", "Borrower Status", new String[] {"Good Standing", "Delinquent"}));

		validator.addValidation(new PresenceValidation("DateOfLatestBorrowerStatus", "Date Borrower Status Updated"));
		validator.addValidation(new DateValidation("DateOfLatestBorrowerStatus", "Date Borrower Status Updated"));
		
		validator.addValidation(new PresenceValidation("DateOfFirstRegistration", "Date Registered"));
		validator.addValidation(new DateValidation("DateOfFirstRegistration", "Date Registered"));
		
		NumericValidation penaltyValidation = new NumericValidation("MonetaryPenalty", "Monetary Penalty");
		penaltyValidation.requireGreaterThanOrEqualTo(0);
		penaltyValidation.allowEmpty();
		validator.addValidation(penaltyValidation);
				
		validator.addValidation(new LengthValidation("Notes", "Notes", 0, 300));
	
		validator.addValidation(new InclusionValidation("Status", "Status", new String[] {"Active", "Inactive"}));

		validator.addValidation(new PresenceValidation("DateOfLastUpdate", "Date Updated"));
		validator.addValidation(new DateValidation("DateOfLastUpdate", "Date Updated"));
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
	
	public boolean setInactive(){
		persistentState.setProperty("Status", "Inactive");
		return save();
	}

	@Override
	public boolean isPrimaryKeyAutoIncrement() {
		return false;
	}
	
	@Override
	public boolean beforeValidate(boolean isCreate){
		String currentDate =  DateUtil.getDate();
		if(isCreate){
			persistentState.setProperty("BorrowerStatus", "Good Standing");
			persistentState.setProperty("Status", "Active");
			persistentState.setProperty("MonetaryPenalty", "0.00");
			persistentState.setProperty("DateOfFirstRegistration", currentDate);
		}		
		persistentState.setProperty("DateOfLatestBorrowerStatus", currentDate);
		persistentState.setProperty("DateOfLastUpdate", currentDate);
		return true;
	}
	
	public RentalCollection getOutstandingRentals(Borrower borrower){
		RentalCollection rentals = new RentalCollection();
		rentals.findOutstandingByBorrower(borrower);
		return rentals;
	}
	
	public boolean addMonetaryPenaltyForLostBook(Book book){
		double monetaryPenalty = Double.parseDouble((String)persistentState.get("MonetaryPenalty"));
		monetaryPenalty += Double.parseDouble((String)book.getState("SuggestedPrice"));
		persistentState.setProperty("MonetaryPenalty", NumberUtil.getAsCurrencyString(monetaryPenalty));
		return save();
	}
}
