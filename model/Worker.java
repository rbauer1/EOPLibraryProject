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

import utilities.Key;
import exception.InvalidPrimaryKeyException;

/**
 * Worker model that persists to the database.
 */
public class Worker extends Model {

	public static final String TABLE_NAME = "worker";
	public static final String PRIMARY_KEY = "BannerID";
	
	private static final String SALT = "68352016baee847f64eb6c2a35eeea67";
	private static Properties schema;

	private static SecureRandom random;
	private String resetToken;

	public Worker(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
	}

	public Worker(Properties persistentState, boolean persisted) {
		super(persistentState);
	}

	public Worker(Properties persistentState) {
		this(persistentState, false);
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


	public boolean validPassword(String password) {
		return persistentState.get("Password").equals(encrypt(password));
	}	
	
	public String getResetToken() {
		  if(random == null){
			  random = new SecureRandom();
		  }
		  resetToken = new BigInteger(130, random).toString(32);
		  return resetToken;
	}

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
