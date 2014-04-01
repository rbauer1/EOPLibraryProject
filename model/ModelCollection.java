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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/**
 * Superclass for all collections of models.
 * Provides methods for finding/adding/sorting entities
 */
public abstract class ModelCollection<T extends Model> extends EntityBase {
	
	/** Holds all the models entities */
	private List<T> entities;

	/**
	 * Constructs a new Model Collection
	 */
	protected ModelCollection() {
		super();
		this.entities = new ArrayList<T>();
	}
	
	/**
	 * Fills collection with all entities in table
	 */
	public void findAll() {
		findByQuery("SELECT * FROM " + getTableName());
	}
	
	/**
	 * Fills collection with all entities in table that match provided whereClause
	 * @param whereClause
	 */
	public void find(String whereClause) {
		findByQuery("SELECT * FROM " + getTableName() + " " + whereClause);
	}
	
	/**
	 * Fills collection with all entities in table that match provided whereClause
	 * @param whereClause - properties object where keys are column names
	 */
	public void find(Properties whereClause) {
		setEntitiesFromQueryResult(getPersistentState(getSchema(), whereClause));
	}
	
	/**
	 * Fills collection with all entities in table that match provided whereClause
	 * Uses Like SQL operator.
	 * @param whereClause - properties object where keys are column names
	 */
	public void findLike(Properties whereClause) {
		setEntitiesFromQueryResult(getPersistentStateLike(getSchema(), whereClause));
	}
	
	/**
	 * Fills collection with all entities in table where column of name key == value
	 * @param key - column name
	 * @param value - column value
	 */
	public void findByKey(String key, String value) {
		if(key == null || key.length() == 0 || value == null || value.length() == 0){
			throw new IllegalArgumentException("Key and value must not be empty.");
		}
		Properties whereClause = new Properties();
		whereClause.setProperty(key, value);
		find(whereClause);
	}
	
	/**
	 * Fills collection with results from provided query
	 * @param query
	 */
	protected void findByQuery(String query){
		setEntitiesFromQueryResult(getSelectQueryResult(query));
	}
	
	/**
	 * Adds model entity to collection. This does not modify or save to the db. 
	 * @param entity
	 */
	public void add(T entity) {
		if(entity != null){
			this.entities.add(entity);
		}
	}

	/**
	 * Get entity in collection where entity attribute referenced by key == value
	 * If more than one entry exists the first one is retrieved.
	 * @param key
	 * @param value
	 */
	public T get(String key, Object value) {
		for (T entity : this.entities) {
			if (entity.getState(key).equals(value)) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * Removes first occurrence from collection where entity attribute referenced by key == value
	 * @param key
	 * @param value
	 */
	public T remove(String key, Object value) {
		int index = 0;
		for (T entity : this.entities) {
			if (entity.getState(key).equals(value)) {
				return this.entities.remove(index);
			}
			index++;
		}
		return null;
	}
	
	/**
	 * Performs validations on all enitities
	 * @return true if all entities are valid
	 */
	public boolean validate() {
		boolean valid = true;
		for (Model entity : this.entities) {
			valid &= entity.validate();
		}
		return valid;
	}

	/**
	 * Saves all entities 
	 * @return true if all saved successfully
	 */
	public boolean save() {
		if(!validate()){
			return false;
		}
		boolean success = true;
		for (Model entity : this.entities) {
			success &= entity.save();
		}
		return success;
	}
	
	/**
	 * @return true if all entities are persisted
	 */
	public boolean isPersisted() {
		boolean persisted = false;
		for (Model entity : this.entities) {
			persisted |= entity.isPersisted();
		}
		return persisted;	
	}

	/**
	 * Sorts entities in collection using provided comparator
	 * @param comparator
	 */
	public void sort(Comparator<T> comparator) {
		Collections.sort(this.entities, comparator);
	}
	
	/**
	 * Sorts entities in collection in ascending order by value referenced by provided key.
	 * @param key - column name
	 */
	public void sort(final String key) {
		sort(key, false);
	}
	
	/**
	 * Sorts entities in collection in ascending order by value referenced by provided key.
	 * Sorted in descending order if reverse is true.
	 * @param key - column name
	 * @param reverse - descending order if true, ascending order if false
	 */
	public void sort(final String key, final boolean reverse) {
		Comparator<T> comparator = new Comparator<T>(){

			@Override
			public int compare(T entity1, T entity2) {
				String value1 = (String)entity1.getState(key);
				String value2 = (String)entity2.getState(key);
				if(value1 == null || value2 == null){
					throw new IllegalArgumentException("Entity attribute for " + key + " is undefined");
				}
				return (reverse ? -1 : 1) * value1.compareTo(value2);
			}
			
		};
		Collections.sort(this.entities, comparator);
	}

	/**
	 * @return list of entities in collection
	 */
	public List<T> getEntities() {
		return this.entities;
	}
	
	@Override
	public Object getState(String key) {
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		registry.updateSubscribers(key, this);
	}

	/**
	 * Returns a property object of schema. Creates it if not exist.
	 * @return schema
	 */
	protected abstract Properties getSchema();
	
	/**
	 * Returns table name.
	 * @return tableName
	 */
	protected abstract String getTableName();

	/**
	 * Creates entity from state from db. Sets entity as persisted.
	 * @return entity
	 */
	protected abstract T createEntity(Properties persistentState);

	/**
	 * Fills collections with the results from a query.
	 * Empties any entities that already exist in collections
	 * @param result - list of Properties from query
	 */
	protected void setEntitiesFromQueryResult(List<?> result){
		this.entities = new ArrayList<T>();
		if (result != null) {
			for(Object state : result){
				if(state instanceof Properties){
					T entity = this.createEntity((Properties)state);
					entity.setPersisted(true);
					add(entity);
				}
			}
		}
	}
}
