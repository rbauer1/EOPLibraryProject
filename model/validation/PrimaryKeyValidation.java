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
public class PrimaryKeyValidation extends Validation {

	/** Error message */
	private String message;

	/**
	 * Constructs a validation that ensures the field is unique within table.
	 * Default Message is "fieldName is already used".
	 * @param fieldKey
	 * @param fieldName
	 */
	public PrimaryKeyValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "is already used");
	}

	/**
	 * Constructs a validation that ensures the field is unique within table.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public PrimaryKeyValidation(String fieldKey, String fieldName, String message) {
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
		if((model.isCreateOperation() ? 0 : 1) != getNumberOfMatches(model.getSchema(), getFieldName(), (String)value)){
			validator.addError(getFieldKey(), getFieldName() + " " + message);
			return false;
		}
		return true;
	}

	protected int getNumberOfMatches(Properties schema, String field, String fieldValue) {
		try {
			Properties whereClause = new Properties();
			whereClause.setProperty(field, fieldValue);
			SQLStatement sqlStatement = new SQLSelectStatement(schema, whereClause);
			Statement statement = JDBCBroker.getInstance().getConnection().createStatement();
			statement.setMaxRows(2);
			ResultSet queryResults = statement.executeQuery(sqlStatement.toString());
			int numResults = 0;
			while (queryResults.next() && numResults <= 2) {
				numResults++;
			}
			queryResults.close();
			statement.close();
			return numResults;
		} catch (SQLException sqle) {
			new Event(Event.getLeafLevelClassName(this), "getSelectQueryResult",
					"SQL Exception: " + sqle.getErrorCode() + ": " + sqle.getMessage(), Event.ERROR);
			return 0;
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