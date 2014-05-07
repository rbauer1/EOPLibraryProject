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
 * Transacation responsible for deleting books
 */
public class DeleteBooksTransaction extends Transaction {

	/** Book Model this transaction is updating */
	private Book book;

	/** Transaction for listing books */
	private Transaction listBooksTransaction;

	/**
	 * Constructs Delete Books Transaction
	 * @param parentController
	 */
	public DeleteBooksTransaction(Controller parentController) {
		super(parentController);
	}

	/**
	 * Deletes the selected book. Uses the provided bookData to generate notes to identify delete reason.
	 * @param bookData
	 */
	private void deleteBook(final Properties bookData){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				String notes = "Reason For Deletion: " + bookData.getProperty("DeletionReason", "None") + "\n";
				notes += bookData.getProperty("Notes", "");
				return book.setInactive(notes);
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
					stateChangeRequest(Key.BACK, "ListBooksView");
					listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The book was deleted successfully."));
				}else{
					List<String> inputErrors = book.getErrors();
					if(inputErrors.size() > 0){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
					}else{
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while deleting."));
					}
				}
			}
		}.execute();
	}

	@Override
	public void execute(){
		listBooksTransaction = TransactionFactory.executeTransaction(this, "ListBooksTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BOOK);
		listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a book from the list below to delete."));
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
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			book = (Book)value;
			showView("DeleteBookView");
		}else if(key.equals(Key.SAVE_BOOK)){
			deleteBook((Properties)value);
		}else if(key.equals(Key.BACK)){
			listBooksTransaction.execute();
			listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a book from the list below to delete."));
		}
		super.stateChangeRequest(key, value);
	}
}
