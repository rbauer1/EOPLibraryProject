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


import java.util.Properties;

import model.Book;
import utilities.Key;
import controller.Controller;

public class DeleteBooksTransaction extends Transaction {
	
	private Transaction listBooksTransaction;
	
	/** Book Model this transaction is updating */
	private Book book;

	/**
	 * Constructs Delete Books Transaction
	 * @param parentController
	 */
	public DeleteBooksTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BOOK, Key.BOOK);
		return dependencies;
	}
	
	@Override
	public Object getState(String key) {
		if(key.equals(Key.BOOK)){
			return book;
		}
		return null;
	}
	
	@Override
	public void execute(){
		listBooksTransaction = TransactionFactory.executeTransaction(this, "ListBooksTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BOOK);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			book = (Book)value;
			showView("DeleteBookView");
		}else if(key.equals(Key.SAVE_BOOK)){
			book.setInactive(); //TODO handle delete error
			listBooksTransaction.stateChangeRequest(Key.REFRESH_LIST, null);
		}
		registry.updateSubscribers(key, this);
	}
}
