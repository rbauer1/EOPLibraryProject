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

import java.util.Properties;

import model.Book;
import userinterface.View;
import utilities.Key;

public class ModifyBooksTransaction extends Transaction {
	private Book book;

	public ModifyBooksTransaction() {
		super();
	}

	@Override
	public Object getState(String key) {
		
		return null;
	}
	
	@Override
	public void execute(){
		TransactionFactory.executeTransaction(this, "ListBooksTransaction");
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SUBMIT_NEW_BOOK)){
			submitNewBook((Properties) value);
		}
		registry.updateSubscribers(key, this);
	}

	@Override
	protected void setDependencies() {
		Properties dependencies = new Properties();
		registry.setDependencies(dependencies);
	}

	@Override
	protected View createView() {
		return getView("ListBooksView");
	}

	private void submitNewBook(Properties bookData){
		book = new Book(bookData);
		if(book.save()){
			stateChangeRequest(Key.ADD_BOOK_SUCCESS, null);
		}else{
			stateChangeRequest(Key.INPUT_ERROR, null);
		}
	}
	
}
