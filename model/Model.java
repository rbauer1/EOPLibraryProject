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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.validation.ModelValidator;
import event.Event;
import exception.InvalidPrimaryKeyException;

/**
 * Base class for all entities that that are persisted into the database.
 */
public abstract class Model extends EntityBase {
	
	private boolean persisted;
	protected ModelValidator validator;

	
	/**
	 * Constructor that calls EntityBase constructor and sets persisted.
	 * Only should be called by constructors in this class.
	 * @param persisted - if model is being created from db
	 */
	private Model(boolean persisted){
		super();
		this.persisted = persisted;
		this.validator = new ModelValidator(this);
		setupValidations();
	}

	/**
	 * Constructs a new model from properties object.
	 * @param persistentState
	 */
	protected Model(Properties persistentState) {
		this(persistentState, false);
	}
	
	/**
	 * Constructs a new model from properties object.
	 * @param persistentState
	 * @param persisted
	 */
	protected Model(Properties persistentState, boolean persisted) {
		this(persisted);
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
	 * Hook method to set up validations in subclasses.
	 */
	protected abstract void setupValidations();
	
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
	 * Returns the persistentState of this Model
	 */
	public Properties getPersistentState(){
		return persistentState;
	}
	
	/**
	 * Changes value of column for provided key in persistent state.
	 * This method can be overridden in subclasses to implement specific functionality.
	 * @param key - column name
	 * @param value - value of column
	 */
	public void stateChangeRequest(String key, Object value) {
		this.persistentState.setProperty(key, (String) value);
	}
	
	/**
	 * Calls stateChangeRequest for each key-value pair
	 * @param state 
	 */
	public void stateChangeRequest(Properties state) {
		for (String key : state.stringPropertyNames()) {
			stateChangeRequest(key, state.getProperty(key));
		}
	}
	
	/**
	 * Execute the models validations.
	 * @return true if model is valid
	 */
	public boolean validate() {
		return validator.validate();
	}
	
	/**
	 * Execute the models validations for the provided key.
	 * @return true if validations succeed
	 */
	public boolean validate(String key) {
		return validator.validate(key);
	}
	
	/**
	 * Return the error messages for the model.
	 * @return list errors
	 */
	public List<String> getErrors() {
		List<String> errors = new ArrayList<String>();
		for(List<String> fieldErrors : validator.getErrors().values()){
			if(fieldErrors.size() > 0){
				errors.add(fieldErrors.get(0));
			}
		}
		return errors;
	}
	
	/**
	 * Return the error messages for the provided key.
	 * @return list of errors for the key.
	 */
	public List<String> getErrors(String key) {
		return validator.getErrors(key);
	}
	
	public boolean beforeValidate(boolean isCreate) {
		return true;
	}
	
	public void afterValidate(boolean isCreate) {

	}
	
	/**
	 * Can be used to perform pre-save logic.
	 * Hook method that is called before save. Should be overridden in subclass.
	 * Must return true for save to continue. Returning false will cancel save.
	 * @param isCreate - true if this is insert operation
	 * @return continue save or not
	 */
	public boolean beforeSave(boolean isCreate) {
		return true;
	}
	
	/**
	 * Can be used to implement post-save logic.
	 * Hook method that is called after save.
	 * Called after successful save. Should be overridden by subclasses to 
	 * implement specific functionality.
	 * @param isCreate - true if this is insert operation
	 */
	public void afterSave(boolean isCreate) {

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
				new Event(Event.getLeafLevelClassName(this), "remove",
						"SQL error while removing: " + e.toString(), Event.ERROR);
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
		String keyValue = (String) this.getState(getPrimaryKey());
		boolean isCreate = !this.persisted || keyValue == null || keyValue.length() == 0;
		if(!beforeValidate(isCreate) || !validate()){
			return false;
		}		
		afterValidate(isCreate);
		if(!beforeSave(isCreate)){
			return false;
		}		
		try {
			if (isCreate) {
				insert();
			} else {
				update();
			}
		} catch (SQLException e) {
			new Event(Event.getLeafLevelClassName(this), "save",
					"SQL error while saving: " + e.toString(), Event.ERROR);
			return false;
		}
		this.persisted = true;
		this.afterSave(isCreate);
		return true;
	}

	/**
	 * @return true if this is persisted
	 */
	public boolean isPersisted() {
		return persisted;
	}

	/**
	 * Set if this model is persisted in the db
	 * @param persisted
	 */
	public void setPersisted(boolean persisted) {
		this.persisted = persisted;
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
		for (String key : state.stringPropertyNames()) {
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
