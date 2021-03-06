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
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Book;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles adding a new book.
 */
public class AddBookTransaction extends Transaction {

	/**
	 * Constructs Add Book Transaction
	 * @param parentController
	 */
	public AddBookTransaction(Controller parentController) {
		super(parentController);
	}

	/**
	 * Creates book with provided data and saves it in db.
	 * @param bookData
	 */
	private void addBook(final Properties bookData){
		new SwingWorker<Boolean, Void>() {

			private Book book;

			@Override
			protected Boolean doInBackground() {
				book = new Book(bookData);
				return book.save();
			}

			@Override
			public void done() {
				boolean success = false;
				try {
					success = get();
				} catch (InterruptedException e) {
					success = false;
				} catch (ExecutionException e) {
					success = false;
				}
				if(success){
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The book was sucessfully added."));
				}else{
					List<String> inputErrors = book.getErrors();
					if(inputErrors.size() > 0){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
					}else{
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
					}
				}
			}
		}.execute();
	}

	@Override
	public void execute() {
		showView("AddBookView");
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SAVE_BOOK)){
			addBook((Properties) value);
		}
		super.stateChangeRequest(key, value);
	}
}
