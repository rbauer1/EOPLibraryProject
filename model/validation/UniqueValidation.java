package model.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import model.Model;
import database.JDBCBroker;
import database.SQLSelectStatement;
import database.SQLStatement;
import event.Event;

/**
 * Model Validation that ensures field is unique within table
 */
public class UniqueValidation extends Validation {

	/** Error message */
	private String message;

	/**
	 * Constructs a validation that ensures the field is unique within table.
	 * Default Message is "fieldName is already used".
	 * @param fieldKey
	 * @param fieldName
	 */
	public UniqueValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "is already used");
	}

	/**
	 * Constructs a validation that ensures the field is unique within table.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public UniqueValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName);
		this.message = message;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		//Change to db null
		if(value == null){
			value = "NULL";
		}
		Model model = validator.getModel();
		if(hasResults(model.getSchema(), getFieldName(), (String)value)){
			validator.addError(getFieldKey(), getFieldName() + " " + message);
			return false;
		}
		return true;
	}

	protected boolean hasResults(Properties schema, String field, String fieldValue) {
		try {
			Properties whereClause = new Properties();
			whereClause.setProperty(field, fieldValue);
			SQLStatement sqlStatement = new SQLSelectStatement(schema, whereClause);
			Statement statement = JDBCBroker.getInstance().getConnection().createStatement();
			statement.setMaxRows(1);
			ResultSet queryResults = statement.executeQuery(sqlStatement.toString());
			boolean hasResults = queryResults.isBeforeFirst();
			queryResults.close();
			statement.close();
			return hasResults;
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "getSelectQueryResult",
					"SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
			return false;
		}
	}

	/**
	 * Set error message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}