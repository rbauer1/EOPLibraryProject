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
import model.Borrower;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;
import database.JDBCBroker;

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
		listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a book from the list below to modify."));

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

	/**
	 * Reload the book from the db
	 */
	private void reloadBook() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				book.reload();
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.BOOK, null);
			}
		}.execute();
	}

	/**
	 * Sets the book to be modified. Displays message if lost or inactive.
	 * @param book
	 */
	private void selectBook(Book book){
		this.book = book;
		showView("ModifyBookView");
		if(book.isInactive()){
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Heads Up! This book is archived. It must be recovered before it can be modified."));
		}else if(book.isLost()){
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Heads Up! This book is lost. It must be recovered before it can be modified."));
		}
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			selectBook((Book)value);
		}else if(key.equals(Key.SAVE_BOOK)){
			updateBook((Properties)value);
		}else if(key.equals(Key.RELOAD_ENTITY)){
			reloadBook();
		}else if(key.equals(Key.BACK)){
			listBooksTransaction.execute();
			listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select a book from the list below to modify."));
		}
		super.stateChangeRequest(key, value);
	}

	/**
	 * Updates selected book with provided data
	 * @param bookData
	 */
	private void updateBook(final Properties bookData){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				JDBCBroker.getInstance().startTransaction();
				if(book.isLost()){
					Borrower borrower = book.getBorrowerThatLost();
					if(borrower == null || !borrower.subtractMonetaryPenaltyForLostBook(book)){
						JDBCBroker.getInstance().rollbackTransaction();
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
						return false;
					}
				}
				bookData.setProperty("Status", "Active");
				book.stateChangeRequest(bookData);
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
					JDBCBroker.getInstance().commitTransaction();
					stateChangeRequest(Key.BACK, "ListBooksView");
					listBooksTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The book was sucessfully saved."));
				}else{
					JDBCBroker.getInstance().rollbackTransaction();
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

}
