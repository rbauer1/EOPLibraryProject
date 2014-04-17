/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package database;

import java.util.Properties;

/**
 * Generates the SQL statement to select records from the database.
 */
public class SQLSelectStatement extends SQLStatement {

	/**
	 * Creates SQL Select Statement that fetches values from the db that match as provided.
	 * @param schema - table schema
	 * @param whereValues - condition values
	 */
	public SQLSelectStatement(Properties schema, Properties whereValues) {
		this(schema, whereValues, true);
	}
	
	/**
	 * Creates SQL Select Statement that fetches values from the db that match as provided.
	 * @param schema - table schema
	 * @param whereValues - condition values
	 * @param exactMatch - if true uses LIKE for strings, else uses =
	 */
	public SQLSelectStatement(Properties schema, Properties whereValues, boolean exactMatch) {
		this(schema, schema, whereValues, true);
	}
	
	/**
	 * Creates SQL Select Statement that fetches values from the db that match as provided.
	 * @param schema - table schema
	 * @param projectionSchema - schema of column names to select
	 * @param whereValues - condition values
	 */
	public SQLSelectStatement(Properties schema, Properties projectionSchema, Properties whereValues) {
		this(schema, projectionSchema, whereValues, true);
	}
	
	/**
	 * Creates SQL Select Statement that fetches values from the db that match as provided.
	 * @param schema - table schema
	 * @param projectionSchema - schema of column names to select
	 * @param whereValues - condition values
	 * @param exactMatch - if true uses LIKE for strings, else uses =
	 */
	public SQLSelectStatement(Properties schema, Properties projectionSchema, Properties whereValues, boolean exactMatch) {
		super(schema);
		statement = "SELECT ";
		statement += getColumnNamesList(projectionSchema);
		statement += " FROM " + schema.getProperty("TableName");
		statement += getWhereClause(whereValues, exactMatch);
		statement += ";";
	}
}