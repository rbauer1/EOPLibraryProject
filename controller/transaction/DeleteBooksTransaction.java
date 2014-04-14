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

public class DeleteBooksTransaction extends Transaction {
	
	private Transaction listBooksTransaction;
	
	/** Book Model this transaction is updating */
	private Book book;
	
	/** List of errors in the input */
	private List<String> inputErrors;

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
		if(key.equals(Key.INPUT_ERROR)){
			return inputErrors;
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
			deleteBook((Properties)value);
		}
		registry.updateSubscribers(key, this);
	}
	
	private void deleteBook(Properties bookData){
		String notes = "Reason For Deletion: " + bookData.getProperty("DeletionReason", "None") + "\n";
		notes += bookData.getProperty("Notes", "");
		if(book.setInactive(notes)){
			//TODO show success message
			listBooksTransaction.stateChangeRequest(Key.REFRESH_LIST, null);
			listBooksTransaction.execute();
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
