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

import java.util.List;
import java.util.Properties;

import model.Book;
import model.BookCollection;
import userinterface.View;
import utilities.Key;

public class ListBooksTransaction extends Transaction {
	private BookCollection bookCollection;
	private List<Book> books;
	private Book selectedBook;
	private String operationType;

	public ListBooksTransaction() {
		super();
	}

	@Override
	public Object getState(String key) 
	{
		if(key.equals(Key.GET_BOOK_COLLECTION)){
			return books;
		}else if(key.equals(Key.SELECT_BOOK)){
			return selectedBook;
		}else if(key.equals(Key.MODIFY_OR_DELETE)){
			return operationType;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.GET_BOOK_COLLECTION)){
			getBooks((Properties)value);
		}else if(key.equals(Key.SELECT_BOOK)){
			selectedBook = (Book)value;
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
	
	private void getBooks(Properties props){
		bookCollection = new BookCollection();
		bookCollection.findLike(props);
		books = bookCollection.getEntities();
	}
	
	/**
	 * Either modify or delete
	 * @param operation
	 */
	protected void setOpertationType(String operation){
		operationType = operation;
	}
	
}
