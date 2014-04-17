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
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles modifying a new book.
 */
public class ModifyBooksTransaction extends Transaction {

	/** Book Model this transaction is updating */
	private Book book;

	/** Transaction for listing books */
	private Transaction listBooksTransaction;

	/**
	 * Constructs Modify Books Transaction
	 * @param parentController
	 */
	public ModifyBooksTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute(){
		listBooksTransaction = TransactionFactory.executeTransaction(this, "ListBooksTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BOOK);
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BOOK, Key.BOOK);
		dependencies.setProperty(Key.RELOAD_ENTITY, Key.BOOK);
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
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			book = (Book)value;
			showView("ModifyBookView");
		}else if(key.equals(Key.SAVE_BOOK)){
			updateBook((Properties)value);
		}else if(key.equals(Key.RELOAD_ENTITY)){
			book.reload();
		}else if(key.equals(Key.BACK)){
			listBooksTransaction.execute();
		}
		super.stateChangeRequest(key, value);
	}

	/**
	 * Updates selected book with provided data
	 * @param bookData
	 */
	private void updateBook(Properties bookData){
		bookData.setProperty("Status", "Active");
		book.stateChangeRequest(bookData);
		if(book.save()){
			stateChangeRequest(Key.BACK, "ListBooksView");
			listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The book was sucessfully added."));
		}else{
			List<String> inputErrors = book.getErrors();
			if(inputErrors.size() > 0){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
			}else{
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
			}
		}
	}

}
