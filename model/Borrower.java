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
 * Borrower model that persists to the database.
 */
public class Borrower extends Model {

	public static final String TABLE_NAME = "studentborrower";
	public static final String PRIMARY_KEY = "BannerID";
	
	private static Properties schema;

	public Borrower(String id) throws InvalidPrimaryKeyException {
		super(PRIMARY_KEY, id);
	}

	public Borrower(Properties persistentState, boolean persisted) {
		super(persistentState);
	}

	public Borrower(Properties persistentState) {
		this(persistentState, false);
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
