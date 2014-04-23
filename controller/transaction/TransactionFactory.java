/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package controller.transaction;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import utilities.Key;
import controller.Controller;
import event.Event;

/**
 * Factory Class for creating transactions.
 */
public class TransactionFactory {
	
	private static final String TRANSACTION_PACKAGE = "controller.transaction";
	
	/**
	 * Private Constructor so that this class can't be instantiated.
	 */
	private TransactionFactory(){}
	
	/**
	 * Create a transaction
	 * @param transactionName - class name
	 * @param controller
	 * @return transaction
	 */
	public static Transaction createTransaction(String transactionName, Controller controller){
		try {
			Class<?> clazz = Class.forName(TRANSACTION_PACKAGE + "." + transactionName);
			Constructor<?> ctor = clazz.getConstructor(Controller.class);
			return (Transaction) ctor.newInstance(controller);
		} catch (ClassNotFoundException e) {
			new Event("TransactionFactory", "createTransaction", "Invalid transaction name: " + transactionName, Event.FATAL);
		} catch (InstantiationException e) {
			new Event("TransactionFactory", "createTransaction", "Error occured while instantiating transaction.", Event.FATAL);
		} catch (IllegalAccessException e) {
			new Event("TransactionFactory", "createTransaction", "Illegal Access for provided transaction name.", Event.FATAL);
		} catch (NoSuchMethodException e) {
			new Event("TransactionFactory", "createTransaction", "Constructor does not exist in provided transaction.", Event.FATAL);
		} catch (SecurityException e) {
			new Event("TransactionFactory", "createTransaction", "Invalid security for constructor in transaction. ", Event.FATAL);
		} catch (IllegalArgumentException e) {
			new Event("TransactionFactory", "createTransaction", "Illegal argument pass to transaction constructor.", Event.FATAL);
		} catch (InvocationTargetException e) {
			new Event("TransactionFactory", "createTransaction", "Exception thrown by transcation constructor.", Event.FATAL);
		}
		throw new IllegalArgumentException("Invalid transaction name provided");
	}
	
	
		/**
		 * Begins execution on the transaction with the provided name. 
		 * Listens to the provided returnEvent for completion of transaction.
		 * @param subscriber
		 * @param name
		 * @param returnEvent
		 */
		public static Transaction executeTransaction(Controller controller, String name, String... returnEvent) {
			try {
				Transaction transaction = TransactionFactory.createTransaction(name, controller);
				for(String event : returnEvent) transaction.subscribe(event, controller);
				transaction.execute();
				return transaction;
			} catch (Exception e) {
				e.printStackTrace();
				new Event("TransactionFactory", "executeTransaction",
						"Failure executing transaction: " + e.toString(), Event.ERROR);
			}
			return null;
		}
		
		
		
		/**
		 * Begins execution on the transaction with the provided name. 
		 * Listens to "TranscationCompleted" for completion of transaction.
		 * @param subscriber
		 * @param name
		 */
		public static Transaction executeTransaction(Controller controller, String name) {
			return executeTransaction(controller, name, Key.TRANSACTION_COMPLETED);
		}
	
}
