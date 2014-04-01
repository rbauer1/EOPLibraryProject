package model.validation;


import java.sql.Connection;

import database.JDBCBroker;

public class ForeignKeyValidation extends Validation {
	
	private String tableName;
	private String tableKey;
	private String message;

	public ForeignKeyValidation(String entityStateKey, String fieldName, String tableName, String tableKey, String message) {
		super(entityStateKey, fieldName);
		this.tableKey = tableKey;
		this.tableName = tableName;
		this.message = message;
	}
	
	public ForeignKeyValidation(String entityStateKey, String fieldName, String tableName, String tableKey) {
		this(entityStateKey, fieldName, tableName, tableKey, "is associated incorrectly");
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		Connection connection = JDBCBroker.getInstance().getConnection();
		return true;
	}

}
