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

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;

/**
 * Base class for all entities that that are persisted into the database.
 */
public abstract class Model extends EntityBase {
	
	private boolean persisted;
	
	/**
	 * Constructor that calls EntityBase constructor and sets persisted.
	 * Only should be called by constructors in this class.
	 * @param persisted - if model is being created from db
	 */
	private Model(boolean persisted){
		super();
		this.persisted = persisted;
	}

	/**
	 * Constructs a new model from properties object.
	 * @param persistentState
	 */
	protected Model(Properties persistentState) {
		this(false);
		setPersistentState(persistentState);
	}
	
	/**
	 * Uses a query to create model from db.
	 * Query must ensure that only one record is returned or 
	 * InvalidPrimaryKeyException will be thrown.
	 * @param query 
	 * @throws InvalidPrimaryKeyException
	 */
	protected Model(String query) throws InvalidPrimaryKeyException {
		this(true);
		findByQuery(query);
	}
	
	/**
	 * Creates model from db where key column equals provided value.
	 * @param key - name of column
	 * @param value - value of column
	 * @throws InvalidPrimaryKeyException
	 */
	protected Model(String key, String value) throws InvalidPrimaryKeyException {
		this(true);
		Properties whereClause = new Properties();
		whereClause.setProperty(key, value);
		find(whereClause);
	}
	
	
	/**
	 * Returns a property object of schema. Creates it if not exist.
	 * @return schema
	 */
	public abstract Properties getSchema();
	
	
	/**
	 * Returns table name.
	 * @return tableName
	 */
	public abstract String getTableName();
	
	
	/**
	 * Returns the column name of the primary key.
	 * @return primaryKey
	 */
	public abstract String getPrimaryKey();
	
	
	/**
	 * Returns true if this is an auto incrementing table, else false.
	 * @return isAuto
	 */
	public abstract boolean isPrimaryKeyAutoIncrement();

	/**
	 * Returns the value out of this models properties object.
	 * This method can be overridden in subclasses to implement specific functionality.
	 */
	public Object getState(String key) {
		return this.persistentState.getProperty(key);
	}

	/**
	 * Changes value of column for provided key in persistent state.
	 * This method can be overridden in subclasses to implement specific functionality.
	 * @param key - column name
	 * @value value - value of column
	 */
	public void stateChangeRequest(String key, Object value) {
		this.persistentState.setProperty(key, (String) value);
	}
	
	/**
	 * Can be used to perform pre-save logic.
	 * Hook method that is called before save. Should be overridden in subclass.
	 * Must return true for save to continue. Returning false will cancel save.
	 * @return continue save or not
	 */
	public boolean beforeSave() {
		return true;
	}
	
	/**
	 * Can be used to implement post-save logic.
	 * Hook method that is called after save.
	 * Called after successful save. Should be overridden by subclasses to 
	 * implement specific functionality.
	 */
	public void afterSave() {

	}
	
	/**
	 * Removes this model from db if persisted.
	 * @return true if remove succeeds
	 */
	public boolean remove(){
		Object key = this.getState(getPrimaryKey());
		if(this.persisted && key != null && (String)key != ""){
			try {
				deletePersistentState(getSchema(), persistentState);
			} catch (SQLException e) {
				System.err.println("Error deleting record from database: " + e.getMessage());
				return false;
			}			
		}
		this.persisted = false;
		return true;
	}

	/**
	 * Saves this model to the db. Handles differences in updating and inserting.
	 * @return true if save succeeds
	 */
	public boolean save() {
		if(!beforeSave()){
			return false;
		}
		try {
			String key = (String) this.getState(getPrimaryKey());
			if (this.persisted && key != null && key != "") {
				update();
			} else {
				insert();
			}
		} catch (SQLException e) {
			System.err.println("Error saving record to database: "
					+ e.getMessage());
			return false;
		}
		this.persisted = true;
		this.afterSave();
		return true;
	}

	/**
	 * Inserts a new model into the db.
	 * @throws SQLException
	 */
	protected void insert() throws SQLException {
		if(!isPrimaryKeyAutoIncrement()){
			insertPersistentState(getSchema(), persistentState);
			return;
		}
		int id = insertAutoIncrementalPersistentState(getSchema(), persistentState);
		this.persistentState.setProperty(getPrimaryKey(), id + "");
	}

	/**
	 * Updates old already persisted model in db.
	 * @throws SQLException
	 */
	protected void update() throws SQLException {
		Properties whereClause = new Properties();
		whereClause.setProperty(getPrimaryKey(), persistentState.getProperty(getPrimaryKey()));
		updatePersistentState(getSchema(), persistentState, whereClause);
	}
	
	/**
	 * Finds model by provided query. Query must return only 1 result or exception will be thrown.
	 * @param query
	 * @throws InvalidPrimaryKeyException
	 */
	protected void findByQuery(String query) throws InvalidPrimaryKeyException{
		setPersistentStateFromQueryResult(getSelectQueryResult(query));
	}
	
	/**
	 * Finds model using provided properties where clause. 
	 * Query must return only 1 result or exception will be thrown.
	 * @param whereClause
	 * @throws InvalidPrimaryKeyException
	 */
	protected void find(Properties whereClause) throws InvalidPrimaryKeyException{
		setPersistentStateFromQueryResult(getPersistentState(getSchema(), whereClause));
	}
	
	/**
	 * Sets persistent state using the result of a query. 
	 * Query result must have length of 1 or exception will be thrown.
	 * @param result - list of state vectors from query
	 * @throws InvalidPrimaryKeyException
	 */
	protected void setPersistentStateFromQueryResult(List<?> result) throws InvalidPrimaryKeyException{
		// Check that results contain only one record
		if (result == null || result.size() != 1) {
			throw new InvalidPrimaryKeyException("No record found for provided id.");
		}
		this.persisted = true;
		setPersistentState((Properties)result.get(0));
	}
	
	/**
	 * Sets the persistent state from provide properties object.
	 * @param state
	 */
	protected void setPersistentState(Properties state) {
		this.persistentState = new Properties();
		Enumeration<?> keys = state.propertyNames();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			String value = state.getProperty(key);

			if (value != null && key != null) {
				this.persistentState.setProperty(key, value);
			}
		}
	}
	
	public String toString(){
		return this.persistentState.toString();
	}
}
