/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import event.Event;

/**
 * Base Class for all entities that are to be persisted in database.
 */
abstract public class Persistable {

	/** Database connection broker */
	private JDBCBroker broker;

	/** Max number of rows to return */
	private static final int MAX_ROWS = 20000;

	/** SQL statement to execute */
	private Statement statement;
	
	/** Connection to the db */
	private Connection connection;
	
	/**
	 * Sets up reference to db broker
	 */
	protected Persistable() {
		broker = JDBCBroker.getInstance();
	}

	/**
	 * Create a Properties object representing aspects of the 'schema' of a
	 * table - namely, the column names and the types of the columns
	 * @param tableName - Table name to get schema information for
	 * @return Properties object indicating column names as keys and column
	 *         types as values
	 */
	protected Properties getSchemaInfo(String tableName) {
		try {
			openConnection();

			// extract the metadata from the database
			DatabaseMetaData dbMetaData = connection.getMetaData();

			// create a schema
			Properties schema = new Properties();
			
			// add the name of our table to the schema
			schema.setProperty("TableName", tableName);

			// get the names of the columns from the database
			ResultSet columns = dbMetaData.getColumns(null, null, tableName, null);
			while (columns.next()) {
				String typeValue = columns.getString(6).toLowerCase();
				if (typeValue.startsWith("smallint") || typeValue.startsWith("mediumint") || typeValue.startsWith("int")) {
					typeValue = "numeric";
				} else {
					typeValue = "text";
				}

				// add the column / field name and type to the return props
				schema.setProperty(columns.getString(4), typeValue);
			}
			columns.close();
			return schema;
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "getSchemaInfo",
					"SQL Exception: " + sqle.getErrorCode() + ": "
							+ sqle.getMessage(), Event.ERROR);
			return null;
		}
	}

	/**
	 * Create and execute a SQL statement to fetch the required fields from
	 * the database.
	 * @param schema
	 * @param whereClause
	 * @return list with each element being a Properties object containing the columnName=columnValue mappings
	 */
	protected List<Properties> getPersistentState(Properties schema, Properties whereClause) {
		return getSelectQueryResult(new SQLSelectStatement(schema, whereClause).toString());
	}
	
	/**
	 * Create and execute a SQL statement to fetch the required fields from
	 * the database. Matches using SQL Like operator.
	 * @param schema
	 * @param whereClause
	 * @return list with each element being a Properties object containing the columnName=columnValue mappings
	 */
	protected List<Properties> getPersistentStateLike(Properties schema, Properties whereClause) {
		return getSelectQueryResult(new SQLSelectStatement(schema, schema, whereClause, false).toString());
	}

	/**
	 * Create and execute a SQL statement to fetch required fields from the database.
	 * Column values must be similar/like where clause values.
	 * @param schema
	 * @param projectionSchema
	 * @param where
	 * @return list of Properties objects containing columnName=columnValue mappings
	 */
	protected List<Properties> getQueriedState(Properties schema, Properties projectionSchema, Properties whereClause) {
		return getSelectQueryResult(new SQLSelectStatement(schema, projectionSchema, whereClause, false).toString());
	}

	/**
	 * Create and execute a SQL statement to fetch required fields from the database.
	 * Column values must be exactly equal to where clause values.
	 * @param schema
	 * @param projectionSchema
	 * @param where
	 * @return list of Properties objects containing columnName=columnValue mappings
	 */
	protected List<Properties> getQueriedStateWithExactMatches(Properties schema, Properties projectionSchema, Properties whereClause) {
		return getSelectQueryResult(new SQLSelectStatement(schema, projectionSchema, whereClause, true).toString());
	}

	/**
	 * Execute the SQL SELECT statement specified by the String parameter to
	 * extract the required fields from the database.
	 * @param sqlStatement
	 * @return List with each element being a Properties object containing the 
	 * columnName=columnValue mappings
	 */
	protected List<Properties> getSelectQueryResult(String sqlStatement) {
		try {
			openConnection();
			
			// Verify sql statement
			if(sqlStatement == null || sqlStatement.length() == 0){
				throw new IllegalArgumentException();
			}
			 
			openStatement();

			// Execute the query
			ResultSet queryResults = statement.executeQuery(sqlStatement);

			// Verify connection
			if (queryResults == null) {
				new Event(Event.getLeafLevelClassName(this), "getSelectQueryResult", "Invalid result set from db!", Event.FATAL);
				return null;
			}

			// Get the column information from the ResultSet
			ResultSetMetaData rsMetaData = queryResults.getMetaData();
			int numColumns = rsMetaData.getColumnCount();

			List<Properties> results = new ArrayList<Properties>();			
			while (queryResults.next()) {
				Properties row = new Properties();
				for (int i = 1; i <= numColumns; i++) {
					String key = (String) rsMetaData.getColumnName(i);
					String value = queryResults.getString(i);
					if (value != null) {
						row.setProperty(key, value);
					}
				}
				results.add(row);
			}
			queryResults.close();
			return results;
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "getSelectQueryResult",
					"SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
			return null;
		} finally {
			closeStatement();
		}
	}

	/**
	 * Update values in database for the provided whereClause.
	 * @param schema
	 * @param values
	 * @param whereClause
	 * @return number of rows affected
	 * @throws SQLException 
	 */
	protected Integer updatePersistentState(Properties schema, Properties values, Properties whereClause) throws SQLException{
		return executeUpdateQuery(new SQLUpdateStatement(schema, values, whereClause).toString());
	}

	/**
	 * Executes a generic Insert/Update/Delete query that is provided.
	 * @param sqlStatement
	 * @return number of rows affected
	 * @throws SQLException 
	 */
	protected Integer executeUpdateQuery(String sqlStatement) throws SQLException{
		try {
			openConnection();
			
			// Verify sql statement
			if(sqlStatement == null || sqlStatement.length() == 0){
				throw new IllegalArgumentException();
			}
			 
			openStatement();

			// Execute the query
			return new Integer(statement.executeUpdate(sqlStatement));
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "executeUpdateQuery",
					"SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
			throw sqle;
		} finally {
			closeStatement();
		}
	}
	
	/**
	 * Creates and executes a SQL statement to insert the provide values into the database.
	 * @param schema
	 * @param values
	 * @return the auto generated primary key value
	 * @throws SQLException 
	 */
	protected Integer insertAutoIncrementalPersistentState(Properties schema, Properties values) throws SQLException{
		try {
			openConnection();
			
			SQLInsertStatement sqlStatement = new SQLInsertStatement(schema, values);
			
			openStatement();

			// Execute the query
			statement.executeUpdate(sqlStatement.toString(), Statement.RETURN_GENERATED_KEYS);
			
			ResultSet results = statement.getGeneratedKeys();

			if (results.next()) {
				return new Integer(results.getInt(1));
			} else {
				new Event(Event.getLeafLevelClassName(this), "insertAutoIncrementalPersistentState",
						"Unable to get auto-incremented primary key.", Event.ERROR);
				return null;
			}			
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "insertAutoIncrementalPersistentState",
					"SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
			throw sqle;
		} finally {
			closeStatement();
		}
	}

	/**
	 * Creates executes a SQL statement to insert the provide values into the database.
	 * @param schema
	 * @param values
	 * @return number of rows affected
	 * @throws SQLException 
	 */
	protected Integer insertPersistentState(Properties schema, Properties values) throws SQLException{
		return executeUpdateQuery(new SQLInsertStatement(schema, values).toString());
	}

	/**
	 * Creates and executes a SQL statement to delete the rows from the database that 
	 * match the provided whereClause.
	 * @param schema
	 * @param whereClause
	 * @return number of rows affected
	 * @throws SQLException 
	 */
	protected Integer deletePersistentState(Properties schema, Properties whereClause) throws SQLException{
		return executeUpdateQuery(new SQLDeleteStatement(schema, whereClause).toString());
	}
	
	/**
	 * Open database connection
	 */
	private void openConnection() {
		// Connect to db
		connection = broker.getConnection();
		
		// Verify connection
		if (connection == null) {
			new Event(Event.getLeafLevelClassName(this), "insertAutoIncrementalPersistentState", "Cannot connect to the database!", Event.FATAL);
		}
	}
	
	/**
	 * Open sql statement
	 */
	private void openStatement() {
		try {
			// Create new statement from connection
			statement = connection.createStatement();
			statement.setMaxRows(MAX_ROWS);
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "openStatement",
					"SQL Exception: " + sqle.getErrorCode() + ": "
							+ sqle.getMessage(), Event.ERROR);
		}
	}
	
	/**
	 * Close the open statement
	 */
	private void closeStatement() {
		try {
			if (statement != null) {
				statement.close();
				statement = null;
			}
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "closeStatement",
					"SQL Exception: " + sqle.getErrorCode() + ": "
							+ sqle.getMessage(), Event.ERROR);
		}
	}
}