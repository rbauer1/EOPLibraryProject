/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package database;

import java.util.Properties;

/**
 * Generates the SQL statement to delete records in the database.
 */
public class SQLDeleteStatement extends SQLStatement {

	/**
	 * Creates SQL Delete Statement that removes values from the db that match as provided.
	 * @param schema - table schema
	 * @param whereValues - condition values
	 */
	public SQLDeleteStatement(Properties schema, Properties whereValues) {
		super(schema);
		statement = "DELETE FROM " + schema.getProperty("TableName");
		statement += getWhereClause(whereValues);
		statement += ";";
	}
		
}