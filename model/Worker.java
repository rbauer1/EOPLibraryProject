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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Properties;

import model.validation.AlphaNumericValidation;
import model.validation.BannerIdValidation;
import model.validation.DateValidation;
import model.validation.EmailValidation;
import model.validation.EqualityValidation;
import model.validation.InclusionValidation;
import model.validation.LengthValidation;
import model.validation.PhoneValidation;
import model.validation.PresenceValidation;
import model.validation.PrimaryKeyValidation;
import model.validation.Validation;
import utilities.DateUtil;
import exception.InvalidPrimaryKeyException;

/**
 * Worker model that persists to the database.
 */
public class Worker extends Model {

	public static final String TABLE_NAME = "worker";
	public static final String PRIMARY_KEY = "BannerID";

	/** salt that is append to passwords before encrypting  */
	private static final String SALT = "68352016baee847f64eb6c2a35eeea67";

	/** Schema for the related table */
	private static Properties schema;

	/** Random generator for generating password reset code */
	private static SecureRandom random;

	/**
	 * Encrypts a string using an MD5 hash
	 * @param token
	 * @return encrypted string
	 */
	private static String encrypt(String token) {
		try{
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Add salt to digest
			md.update(SALT.getBytes());
			//Add password to digest
			byte[] bytes = md.digest(token.getBytes());
			//This bytes[] has bytes in decimal format;
			//Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString().substring(0, 20);
		} catch (NoSuchAlgorithmException e) {
			return token;
		}
	}

	/** Validation that is dynamically added for password changes */
	Validation passwordLengthValidation;

	/**
	 * Constructs new Worker from properties object that has not been persisted yet.
	 * @param persistentState
	 */
	public Worker(Properties persistentState) {
		this(persistentState, false);
	}

	/**
	 * Constructs new Worker from properties object.
	 * Must specify if it is persisted.
	 * @param persistentState
	 * @param persisted
	 */
	public Worker(Properties persistentState, boolean persisted) {
		super(persistentState, persisted);
	}

	/**
	 * Constructs Worker by querying db with primary key.
	 * @param id - primary key
	 * @throws InvalidPrimaryKeyException if query doesn't return 1 result
	 */
	public Worker(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
	}

	@Override
	public void afterValidate(boolean isCreate){
		String passwordChangeType = (String) getState("PasswordChangeType");
		if(passwordChangeType != null && passwordChangeType.equals("CodeReset")){
			validator.removeValidation(passwordLengthValidation);
		}
	}

	@Override
	public boolean beforeValidate(boolean isCreate){
		String password = persistentState.getProperty("NewPassword");
		// New Password is set which means the password is being saved
		if(password != null && password.length() > 0){
			stateChangeRequest("Password", password);
		}

		String passwordChangeType = (String) getState("PasswordChangeType");
		if(passwordChangeType != null && passwordChangeType.equals("CodeReset")){
			validator.addValidation(passwordLengthValidation);
		}

		String currentDate =  DateUtil.getDate();
		if(isCreate){
			persistentState.setProperty("DateOfHire", currentDate);
			persistentState.setProperty("Status", "Active");
		}
		persistentState.setProperty("DateOfLatestCredentialsStatus", currentDate);
		persistentState.setProperty("DateOfLastUpdate", currentDate);
		return true;
	}

	/**
	 * Generate a password reset token for worker
	 * @return random string of length 15
	 */
	public String createPasswordResetCode() {
		if(random == null){
			random = new SecureRandom();
		}
		String passwordResetCode = new BigInteger(130, random).toString(32).substring(0, 15);
		stateChangeRequest("PasswordResetCode", passwordResetCode);
		return passwordResetCode;
	}

	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	@Override
	public Properties getSchema() {
		if (schema == null) {
			schema = getSchemaInfo(TABLE_NAME);
		}
		return schema;
	}

	@Override
	public Object getState(String key){
		if(key.equals("Name")){
			return getState("FirstName") + " " + getState("LastName");
		}
		return super.getState(key);
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	public boolean isAdmin(){
		return persistentState.getProperty("Credentials","").equals("Administrator");
	}

	@Override
	public boolean isPrimaryKeyAutoIncrement() {
		return false;
	}

	public boolean setInactive(String notes){
		persistentState.setProperty("Notes", notes);
		persistentState.setProperty("Status", "Inactive");
		return save();
	}

	@Override
	protected void setupValidations(){
		validator.addValidation(new PresenceValidation("BannerID", "Banner Id"));
		validator.addValidation(new BannerIdValidation("BannerID", "Banner Id"));
		validator.addValidation(new PrimaryKeyValidation("BannerID", "Banner Id"));

		validator.addValidation(new PresenceValidation("Password", "Password"));
		validator.addValidation(new LengthValidation("NewPassword", "New Password", 6, 50, true));
		validator.addValidation(new EqualityValidation("NewPassword", "New Password", "NewPasswordConfirmation", "Password Confirmation"));
		passwordLengthValidation = new LengthValidation("NewPassword", "New Password", 6, 50);

		validator.addValidation(new EqualityValidation("PasswordResetCode", "Password Reset Code", "PasswordResetCodeConfirmation", "provided reset code"));

		validator.addValidation(new PresenceValidation("FirstName", "First Name"));
		validator.addValidation(new AlphaNumericValidation("FirstName", "First Name"));

		validator.addValidation(new PresenceValidation("LastName", "Last Name"));
		validator.addValidation(new AlphaNumericValidation("LastName", "Last Name"));

		validator.addValidation(new PresenceValidation("ContactPhone", "Phone"));
		validator.addValidation(new PhoneValidation("ContactPhone", "Phone"));

		validator.addValidation(new PresenceValidation("Email", "Email"));
		validator.addValidation(new EmailValidation("Email", "Email"));

		validator.addValidation(new InclusionValidation("Credentials", "Credentials", new String[] {"Ordinary", "Administrator"}));

		validator.addValidation(new PresenceValidation("DateOfLatestCredentialsStatus", "Date Credentials Updated"));
		validator.addValidation(new DateValidation("DateOfLatestCredentialsStatus", "Date Credentials Updated"));

		validator.addValidation(new PresenceValidation("DateOfHire", "Date Registered"));
		validator.addValidation(new DateValidation("DateOfHire", "Date Registered"));

		validator.addValidation(new LengthValidation("Notes", "Notes", 0, 300));

		validator.addValidation(new InclusionValidation("Status", "Status", new String[] {"Active", "Inactive"}));

		validator.addValidation(new PresenceValidation("DateOfLastUpdate", "Date Updated"));
		validator.addValidation(new DateValidation("DateOfLastUpdate", "Date Updated"));
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals("Password")){
			value = encrypt((String) value);
		}
		super.stateChangeRequest(key, value);
	}

	/**
	 * Checks if provided password matches worker's password.
	 * @param password- inputted by user
	 * @return true if passwords match
	 */
	public boolean validPassword(String password) {
		return persistentState.get("Password").equals(encrypt(password));
	}
}
