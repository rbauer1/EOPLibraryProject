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

import controller.Controller;


/**
 * Abstract class for all transactions in the system.
 */
public abstract class Transaction extends Controller {

	/**
	 * Constructs a transaction with the provided controller as a parent.
	 * parentController can be another transaction or a main controller.
	 * @param parentController
	 */
	protected Transaction(Controller parentController) {
		super(parentController);
	}
	
	/**
	 * Begins execution of the Transaction. 
	 */
	public abstract void execute();

}
