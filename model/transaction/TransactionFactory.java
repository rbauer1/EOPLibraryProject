/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package model.transaction;

import event.Event;

/**
 * Factory Class for creating transactions.
 */
public class TransactionFactory {
	
	private static final String TRANSACTION_PACKAGE = "model.transaction.";
	
	/**
	 * Private Constructor so that this class can't be instantiated.
	 */
	private TransactionFactory(){}
	
	public static Transaction createTransaction(String transactionName){
		try {
			return (Transaction) Class.forName(TRANSACTION_PACKAGE + transactionName).newInstance();
		} catch (ClassNotFoundException e) {
			new Event("TransactionFactory", "createTransaction",
					"Invalid transaction name.", Event.FATAL);
		} catch (InstantiationException e) {
			new Event("TransactionFactory", "createTransaction",
					"Error occured while instantiating transaction.", Event.FATAL);
		} catch (IllegalAccessException e) {
			new Event("TransactionFactory", "createTransaction",
					"Illegal Access for provided transaction name.", Event.FATAL);
		}
		throw new IllegalArgumentException("Invalid transaction name provided");
	}
	
}
