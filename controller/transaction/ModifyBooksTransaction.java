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

/**
 * Transaction that handles modifying a new book.
 */
public class ModifyBooksTransaction extends Transaction {
	
	/** Book Model this transaction is updating */
	private Book book;
	
	/** List of errors in the input */
	private List<String> inputErrors;
	
	/**
	 * Constructs Modify Books Transaction
	 * @param parentController
	 */
	public ModifyBooksTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BOOK, Key.BOOK);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.BOOK);
		return dependencies;
	}
	
	@Override
	public void execute(){
		TransactionFactory.executeTransaction(this, "ListBooksTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BOOK);
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BOOK)){
			return book;
		}
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
		}
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){	
			book = (Book)value;
			showView("ModifyBookView");
		}else if(key.equals(Key.SAVE_BOOK)){
			updateBook((Properties)value);
		}else if(key.equals(Key.RELOAD_ENTITY)){
			book.reload();
		}
		registry.updateSubscribers(key, this);
	}
	
	/**
	 * Updates selected book with provided data
	 * @param bookData
	 */
	private void updateBook(Properties bookData){
		bookData.setProperty("Status", "Active");
		book.stateChangeRequest(bookData);
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
