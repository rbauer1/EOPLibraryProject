/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package database;

import java.util.Properties;

/**
 * Generates the SQL statement to insert records in the database.
 */
public class SQLInsertStatement extends SQLStatement {

	/**
	 * Creates SQL Insert Statement that inserts into db.
	 * @param schema
	 * @param insertValues
	 */
	public SQLInsertStatement(Properties schema, Properties insertValues) {
		super(schema);
		statement = "INSERT INTO " + schema.getProperty("TableName");
		statement += " ( " + getColumnNamesList(insertValues) + " ) ";
		statement += " VALUES ( " + getValues(insertValues) + " ) ";
		statement += ";";
	}
	
	/**
	 * Builds list of values of columns in schema for use in queries
	 * @param insertValues
	 * @return comma separated list of values
	 */
	private String getValues(Properties insertValues){
		String values = "";		
		for(String columnName : insertValues.stringPropertyNames()){
			if(schema.containsKey(columnName)){
				if (values.length() > 0) {
					values += " , ";
				}				
				String columnValue = insertEscapes(insertValues.getProperty(columnName));
				String columnType = schema.getProperty(columnName);
				if (columnValue.equals("NULL")) {
					values += "NULL";
				} else if (columnType.equals("numeric")) {
					values += columnValue;
				} else {
					values += "'" + columnValue + "'";
				}			
			}
		}
		return values;
	}
}
