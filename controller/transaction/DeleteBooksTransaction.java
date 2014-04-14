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
import userinterface.message.MessageEvent;
import utilities.Key;
import controller.Controller;

public class DeleteBooksTransaction extends Transaction {
	
	/** Transaction for listing books */
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
		return super.getState(key);
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
		super.stateChangeRequest(key, value);
	}
	
	private void deleteBook(Properties bookData){
		String notes = "Reason For Deletion: " + bookData.getProperty("DeletionReason", "None") + "\n";
		notes += bookData.getProperty("Notes", "");
		if(book.setInactive(notes)){
			listBooksTransaction.execute();
			listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent("Success", "Good Job! The book was deleted successfully."));
		}else{
			List<String> inputErrors = book.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent("Error", "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent("Error", "Whoops! An error occurred while deleting."));
			}
		}
	}
}
