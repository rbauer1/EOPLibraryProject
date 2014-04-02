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
	private ListBooksTransaction listBooksTransaction;
	private Book book;
	public ModifyBooksTransaction() {
		super();
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.SELECT_BOOK)){
			return book;
		}
		return null;
	}
	
	@Override
	public void execute(){
		listBooksTransaction = 
				(ListBooksTransaction) TransactionFactory.executeTransaction(this, "ListBooksTransaction", 
						Key.BACK_TO_BOOK_MENU, Key.SELECT_BOOK, Key.MODIFY_OR_DELETE);
		listBooksTransaction.setOpertationType("Modify");
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			book = (Book)value;
			showView("AddBookView");
		}else if(key.equals(Key.SUBMIT_BOOK)){
			submitModifiedBook((Properties)value);
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
		return null;
	}
	
	private void submitModifiedBook(Properties bookData){
		book = new Book(bookData,true);
		if(book.save()){
			stateChangeRequest(Key.BOOK_SUBMIT_SUCCESS, null);
		}else{
			stateChangeRequest(Key.INPUT_ERROR, null);
		}
	}
}	
