/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import common.PropertyFile;

import event.Event;

/**
 * Manages the connection to the database. This is a singleton class.
 * Application can only have one connection to the database.
 */
public class JDBCBroker {
	
	private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DATABASE_CONFIG = "assets/dbConfig.ini";
	
	private static JDBCBroker instance;

	private Connection connection;
	private Driver driver;
	private String databaseName = null;
	private String username = null;
	private String password = null;
	private String server = null;
	
	private boolean openTransaction = false;

	/**
	 * Constructs jdbc broker.
	 */
	private JDBCBroker() {
		Properties properties = new PropertyFile(DATABASE_CONFIG);
		databaseName = properties.getProperty("dbName");
		username = properties.getProperty("username");
		password = properties.getProperty("password");
		server = properties.getProperty("server");
		if (server == null){
			server = "localhost";
		}
		
		try {
			driver = (Driver) Class.forName(DATABASE_DRIVER).newInstance();
		} catch (ClassNotFoundException exc) {
			new Event(Event.getLeafLevelClassName(this), "init", "Could not load driver class[" + DATABASE_DRIVER + "]", Event.ERROR);
		} catch (InstantiationException exc) {
			new Event(Event.getLeafLevelClassName(this), "init", "Could not load driver class[" + DATABASE_DRIVER + "]", Event.ERROR);
		} catch (IllegalAccessException exc) {
			new Event(Event.getLeafLevelClassName(this), "init", "Could not load driver class[" + DATABASE_DRIVER + "]", Event.ERROR);
		}
	}
	
	/**
	 * Returns an instance of the database broker
	 * @return database broker
	 */
	public static synchronized JDBCBroker getInstance() {
		if (instance == null) {
			instance = new JDBCBroker();
		}
		return instance;
	}

	/**
	 * Fetches a connection to the database.
	 * Opens a new connection if one doesn't yet exist.
	 * @return connection to database
	 */
	public Connection getConnection() {
		if (connection == null && databaseName != null && username != null && password != null) {
			try {
				// Create a connection to the database
				connection = driver.connect("jdbc:mysql://" + server + ":3306/"
						+ databaseName + "?" + "user=" + username + "&password=" + password, null);
			} catch (SQLException exc) {
				new Event(Event.getLeafLevelClassName(this), "getConnection", "Could not connect to database", Event.ERROR);
			}
		}
		return connection;
	}
	

	/**
	 * Closes the connection to the database if one exists.
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException exc) {
				new Event(Event.getLeafLevelClassName(this), "closeConnection", "Could not release connection", Event.WARNING);
			}
		}
	}
	
	/**
	 * Starts a new transaction to the database.
	 * If this is called while a previous transaction is active, the previous transaction is committed.
	 */
	public void startTransaction() {
		if(openTransaction){
			commitTransaction();
		}
		Connection connection = getConnection();
		try {
			openTransaction = true;
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			new Event(Event.getLeafLevelClassName(this), "startTransaction", "Could not start transaction", Event.ERROR);
		}
	}
	
	/**
	 * Commit the transaction to the database.
	 * No action if no transaction open
	 */
	public void commitTransaction() {
		if(!openTransaction){
			return;
		}
		Connection connection = getConnection();
		try {
			connection.commit();
		} catch (SQLException e) {
			new Event(Event.getLeafLevelClassName(this), "commitTransaction", "Could not commit transaction", Event.ERROR);
		} finally {
			endTransaction();
		}
	}
	
	/**
	 * Rollsback the current transaction.
	 * No action if no transaction open
	 */
	public void rollbackTransaction() {
		if(!openTransaction){
			return;
		}
		Connection connection = getConnection();
		try {
			connection.rollback();
		} catch (SQLException e) {
			new Event(Event.getLeafLevelClassName(this), "rollbackTransaction", "Could not rollback transaction", Event.ERROR);
		} finally {
			endTransaction();
		}
	}
	
	/**
	 * Ends the active transaction by setting the auto commit to true.
	 */
	protected void endTransaction() {
		Connection connection = getConnection();
		try {
			openTransaction = false;
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			new Event(Event.getLeafLevelClassName(this), "endTransaction", "Could not end transaction", Event.ERROR);
		}
	}
}
