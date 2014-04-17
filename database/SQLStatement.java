/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package database;

import java.util.Properties;

/**
 * Represents an abstract SQL Statement that can be applied to a database.
 */
public abstract class SQLStatement {

	/** Character to escape in provided values */
	private static final String ESCAPE_TARGET = "'";
	
	/** String used to escape single quotes */
	private static final String ESCAPE_REPLACEMENT = "\\'";

	/** Generated SQL statement */
	protected String statement; 
	
	/** Schema for the table */
	protected Properties schema;
	
	/**
	 * @param schema
	 */
	protected SQLStatement(Properties schema){
		this.schema = schema;
		this.statement = "";
	}

	/**
	 * Escapes value in SQL string to prevent SQL Injection.
	 * @param value
	 * @return escaped string
	 */
	protected String insertEscapes(String value) {
		return value.replace(ESCAPE_TARGET, ESCAPE_REPLACEMENT);
	}
	
	/**
	 * Construct where clause from provided values.
	 * Only allows column names in the schema.
	 * @param whereValues
	 * @return whereClause
	 */
	protected String getWhereClause(Properties whereValues) {
		return getWhereClause(whereValues, true);
	}
	
	/**
	 * Construct where clause from provided values.
	 * Only allows column names in the schema.
	 * @param whereValues
	 * @param exactMatch - if true uses LIKE for string, else =
	 * @return whereClause
	 */
	protected String getWhereClause(Properties whereValues, boolean exactMatch) {
		int numConditions = 0;
		String whereClause = " WHERE ";
		if (whereValues != null) {
			for(String columnName : whereValues.stringPropertyNames()){
				if(schema.containsKey(columnName)){
					numConditions++;
					if(!whereClause.equals(" WHERE ")){
						whereClause += " AND ";
					}				
					whereClause += "`" + columnName + "` ";
					String columnValue = insertEscapes(whereValues.getProperty(columnName));
					String columnType = schema.getProperty(columnName).toLowerCase();
					if (columnValue.equals("NULL")) {
						whereClause += "IS NULL";
					} else if (columnType.equals("numeric")) {
						whereClause += "= " + columnValue;
					} else if(exactMatch || columnType.equals("enum")) {
						whereClause += "= '" + columnValue + "'";
					}else {
						whereClause += "LIKE '%" + columnValue + "%'";
					}
				}			
			}
		}
		return numConditions > 0 ? whereClause : "";
	}
	
	/**
	 * Builds list of column names from values in schema for use in queries
	 * @param values
	 * @return Comma separated list of column names in schema
	 */
	protected String getColumnNamesList(Properties values){
		String columnNames = "";
		for(String columnName : values.stringPropertyNames()){
			if(schema.containsKey(columnName) && !columnName.equals("TableName")){
				if (columnNames.length() > 0) {
					columnNames += " , ";
				}
				columnNames += "`" + columnName + "`";	
			}
		}
		return columnNames;
	}

	@Override
	public String toString() {
		System.out.println(statement);
		return statement;
	}
}
