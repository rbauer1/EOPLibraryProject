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
import utilities.Key;
import controller.Controller;

public class AddBookTransaction extends Transaction {
	
	/** Book Model this transaction is adding */
	private Book book;
	
	private List<String> inputErrors;

	/**
	 * Constructs Add Book Transaction
	 * @param parentController
	 */
	public AddBookTransaction(Controller parentController) {
		super(parentController);
	}
	
	
	@Override
	public void execute() {
		showView("AddBookView");
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SUBMIT_BOOK)){
			addBook((Properties) value);
		}
		registry.updateSubscribers(key, this);
	}


	/**
	 * Creates book with provided data and saves it in db.
	 * @param bookData
	 */
	private void addBook(Properties bookData){
		book = new Book(bookData);
		if(book.save()){
			stateChangeRequest(Key.SAVE_SUCCESS, null);
		}else{
			inputErrors = book.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.INPUT_ERROR, null);
			}else{
				stateChangeRequest(Key.SAVE_ERROR, null);
			}
		}
	}
	
}
