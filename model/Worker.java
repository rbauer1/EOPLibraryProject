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

import model.validation.BannerIdValidation;
import model.validation.DateValidation;
import model.validation.InclusionValidation;
import model.validation.PhoneValidation;
import model.validation.PresenceValidation;
import utilities.Key;
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
	
	/** Reset code generated for this worker */
	private String resetCode;

	/**
	 * Constructs Worker by querying db with primary key.
	 * @param id - primary key
	 * @throws InvalidPrimaryKeyException if query doesn't return 1 result
	 */
	public Worker(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
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
	 * Constructs new Worker from properties object that has not been persisted yet.
	 * @param persistentState
	 */
	public Worker(Properties persistentState) {
		this(persistentState, false);
	}
	
	@Override
	protected void setupValidations(){
		validator.addValidation(new PresenceValidation("BannerID", "Banner Id"));
		validator.addValidation(new BannerIdValidation("BannerID", "Banner Id"));
		
		validator.addValidation(new PresenceValidation("Password", "Password"));
		
		validator.addValidation(new PresenceValidation("FirstName", "First Name"));
		validator.addValidation(new BannerIdValidation("FirstName", "First Name"));
		
		validator.addValidation(new PresenceValidation("LastName", "Last Name"));
		validator.addValidation(new BannerIdValidation("LastName", "Last Name"));
		
		validator.addValidation(new PresenceValidation("ContactPhone", "Phone"));
		validator.addValidation(new PhoneValidation("ContactPhone", "Phone"));
		
		validator.addValidation(new PresenceValidation("Email", "Email"));
		validator.addValidation(new PhoneValidation("Email", "Email"));
		
		validator.addValidation(new InclusionValidation("Credentials", "Credentials", new String[] {"Ordinary", "Administrator"}));

		validator.addValidation(new PresenceValidation("DateOfLatestCredentialsStatus", "Date Credentials Updated"));
		validator.addValidation(new DateValidation("DateOfLatestCredentialsStatus", "Date Credentials Updated"));
		
		validator.addValidation(new PresenceValidation("DateOfHire", "Date Registered"));
		validator.addValidation(new DateValidation("DateOfHire", "Date Registered"));
		
		validator.addValidation(new InclusionValidation("ActiveStatus", "Status", new String[] {"Active", "Inactive"}));

		validator.addValidation(new PresenceValidation("DateOfLastUpdate", "Date Updated"));
		validator.addValidation(new DateValidation("DateOfLastUpdate", "Date Updated"));	
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.PW)){
			value = encrypt((String) value);
		}
		super.stateChangeRequest(key, value);
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


	/**
	 * Checks if provided password matches worker's password.
	 * @param password- inputted by user
	 * @return true if passwords match
	 */
	public boolean validPassword(String password) {
		return persistentState.get("Password").equals(encrypt(password));
	}	
	
	/**
	 * Generate a password reset token for worker
	 * @return random string of length 32
	 */
	public String getResetToken() {
		  if(random == null){
			  random = new SecureRandom();
		  }
		  resetCode = new BigInteger(130, random).toString(32);
		  return resetCode;
	}

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
	        for(int i=0; i< bytes.length ;i++){
	            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	        }
			return sb.toString().substring(0, 20);
		} catch (NoSuchAlgorithmException e) {
			return token;
		}
	}
}
