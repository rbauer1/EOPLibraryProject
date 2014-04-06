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


import model.Book;
import utilities.Key;
import controller.Controller;

public class DeleteBooksTransaction extends Transaction {

	/**
	 * Constructs Delete Books Transaction
	 * @param parentController
	 */
	public DeleteBooksTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public Object getState(String key) {
		return null;
	}
	
	@Override
	public void execute(){
		TransactionFactory.executeTransaction(this, "ListBooksTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BOOK);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			((Book)value).setInactive(); //TODO handle error if save fails here!
		}
		registry.updateSubscribers(key, this);
	}
}
