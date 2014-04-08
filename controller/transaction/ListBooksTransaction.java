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

import java.util.List;
import java.util.Properties;

import model.Book;
import model.BookCollection;
import utilities.Key;
import controller.Controller;

public class ListBooksTransaction extends Transaction {
	private BookCollection bookCollection;
	private List<Book> books;
	private Book selectedBook;
	private String operationType;

	/**
	 * Constructs List Books Transaction
	 * @param parentController
	 */
	public ListBooksTransaction(Controller parentController) {
		super(parentController);
		if(parentController instanceof DeleteBooksTransaction){
			operationType = "Delete";
		}
		else if(parentController instanceof ModifyBooksTransaction){
			operationType = "Modify";
		}
	}

	@Override
	public void execute() {
		showView("ListBooksView");
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.GET_BOOK_COLLECTION)) {
			return books;
		}else if(key.equals(Key.SELECT_BOOK)){
			return selectedBook;
		}else if(key.equals(Key.OPERATION_TYPE)){
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
	
	private void getBooks(Properties props){
		bookCollection = new BookCollection();
		bookCollection.findLike(props);
		books = bookCollection.getEntities();
	}
		
}
