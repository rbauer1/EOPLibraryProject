package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

public abstract class ModelCollection<T extends Model> extends EntityBase {
	
	private List<T> entities;

	protected ModelCollection() {
		super();
		this.entities = new ArrayList<T>();
	}
	
	protected abstract Properties getSchema();
	protected abstract String getTableName();
	protected abstract T createEntity(Properties persistentState);

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
	
	public List<T> getEntities() {
		return this.entities;
	}
	
	public void findAll() {
		findByQuery("SELECT * FROM " + getTableName());
	}
	
	public void find(String whereClause) {
		findByQuery("SELECT * FROM " + getTableName() + " " + whereClause);
	}
	
	public void find(Properties whereClause) {
		setEntitiesFromQueryResult(getPersistentState(getSchema(), whereClause));
	}
	
	public void findByKey(String key, String value) {
		if(key == null || key.length() == 0 || value == null || value.length() == 0){
			throw new IllegalArgumentException("Key and value must not be empty.");
		}
		Properties whereClause = new Properties();
		whereClause.setProperty(key, value);
		find(whereClause);
	}
	
	protected void findByQuery(String query){
		setEntitiesFromQueryResult(getSelectQueryResult(query));
	}
	
	public void sort(Comparator<T> comparator) {
		Collections.sort(this.entities, comparator);
	}
	
	public void sort(final String key) {
		sort(key, false);
	}
	
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

	public void add(T entity) {
		if(entity != null){
			this.entities.add(entity);
		}
	}

	public T get(String key, Object value) {
		for (T entity : this.entities) {
			if (entity.getState(key).equals(value)) {
				return entity;
			}
		}
		return null;
	}
	
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
	
	public boolean validate() {
		boolean valid = true;
		for (Model entity : this.entities) {
			//TODO add model validation
			//valid &= entity.validate();
		}
		return valid;
	}

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
	
	public boolean isPersisted() {
		boolean persisted = false;
		for (Model entity : this.entities) {
			persisted |= entity.isPersisted();
		}
		return persisted;	
	}	

}
