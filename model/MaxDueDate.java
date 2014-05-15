package model;

import java.sql.SQLException;
import java.util.Properties;

import utilities.Key;
import event.Event;
import exception.InvalidPrimaryKeyException;
import model.validation.DateValidation;
import model.validation.PresenceValidation;

public class MaxDueDate extends Model{
	public static final String PRIMARY_KEY = "CurrentMaxDueDate";
	/** Schema for the related table */
	private static Properties schema;

	public static final String TABLE_NAME = "maxduedate";
	
	public MaxDueDate() throws InvalidPrimaryKeyException{
		super("SELECT * FROM " + TABLE_NAME);
	}
	
	@Override
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}
	
	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SET_DUE_DATE)){
			persistentState.setProperty("CurrentMaxDueDate",(String) value);
		}
		super.stateChangeRequest(key, value);
	}

	@Override
	public Properties getSchema() {
		if (schema == null) {
			schema = getSchemaInfo(TABLE_NAME);
		}
		return schema;
	}
	
	/**
	 * Saves this model to the db. Handles differences in updating and inserting.
	 * @return true if save succeeds
	 */
	public boolean save() {
		boolean isCreate = false;
		if(!validate() || !beforeSave(isCreate)){
			return false;
		}
		try {
			update();
		} catch (SQLException e) {
			new Event(Event.getLeafLevelClassName(this), "save",
					"SQL error while saving: " + e.toString(), Event.ERROR);
			return false;
		}
		this.setPersisted(true);
		afterSave(isCreate);
		return true;
	}
	
	/**
	 * Updates old already persisted model in db.
	 * @throws SQLException
	 */
	protected void update() throws SQLException {
		Properties whereClause = new Properties();
		whereClause.setProperty("1", "1");
		updatePersistentState(getSchema(), persistentState, whereClause);
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean isPrimaryKeyAutoIncrement() {
		return false;
	}

	@Override
	protected void setupValidations() {
		validator.addValidation(new PresenceValidation("CurrentMaxDueDate", "Max Due Date"));
		validator.addValidation(new DateValidation("CurrentMaxDueDate", "Max Due Date"));
	}

}
