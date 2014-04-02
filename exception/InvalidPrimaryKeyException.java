/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package exception;

/**
 * This class indicates an exception that is thrown if the primary key is not
 * properly supplied to the data access model object as it seeks to retrieve a
 * record from the database
 */
public class InvalidPrimaryKeyException extends Exception {

	private static final long serialVersionUID = -507605116869545753L;

	/**
	 * Constructs exception with provided message.
	 * @param message
	 */
	public InvalidPrimaryKeyException(String message) {
		super(message);
	}
}
