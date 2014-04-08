/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package database;

import java.util.Properties;

/**
 * Generates the SQL statement to update records in the database.
 */
public class SQLUpdateStatement extends SQLStatement {

	/**
	 * Creates SQL Update Statement that updates db with provided values.
	 * @param schema - table schema
	 * @param updateValues - values to update
	 * @param whereValues - condition values
	 */
	public SQLUpdateStatement(Properties schema, Properties updateValues, Properties whereValues) {
		super(schema);
		statement = "UPDATE " + schema.getProperty("TableName");
		statement += getSetStatment(updateValues);
		statement += getWhereClause(whereValues);
		statement += ";";
	}
	
	/**
	 * @param updateValues
	 * @return set part of the query
	 */
	private String getSetStatment(Properties updateValues) {
		String setStatement = "";
		for(String columnName : updateValues.stringPropertyNames()){
			if(schema.containsKey(columnName)){
				if(setStatement.length() > 0){
					setStatement += " , ";
				}				
				setStatement += "`" + columnName + "` ";
				String columnValue = insertEscapes(updateValues.getProperty(columnName));
				String columnType = schema.getProperty(columnName).toLowerCase();
				if (columnValue.equals("NULL")) {
					setStatement += "= NULL";
				} else if (columnType.equals("numeric")){
					setStatement += "= " + columnValue;
				}else{
					setStatement += "= '" + columnValue + "'";
				}
			}			
		}
		return " SET " + setStatement;
	}
	
}