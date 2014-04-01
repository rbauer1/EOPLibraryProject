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

import userinterface.View;
import utilities.Key;
import model.Book;

public class DeleteBooksTransaction extends Transaction {
	private Transaction listBooksTransaction;
	public DeleteBooksTransaction() {
		super();
	}

	@Override
	public Object getState(String key) {
		return null;
	}
	
	@Override
	public void execute(){
		listBooksTransaction = 
				TransactionFactory.executeTransaction(this, "ListBooksTransaction", 
						Key.BACK_TO_BOOK_MENU, Key.SELECT_BOOK);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			((Book)value).setInactive();
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

	private void submitNewBook(Properties bookData){
		
	}
	
}
