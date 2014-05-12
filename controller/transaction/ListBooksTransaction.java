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

import javax.swing.SwingWorker;

import model.Book;
import model.BookCollection;
import utilities.Key;
import controller.Controller;
import controller.LibrarianController;
import document.DocumentFactory;
import document.ExcelDocument;

/**
 * Transaction that handles listing and selecting a book
 */
public class ListBooksTransaction extends Transaction {

	/** list of books returned from search */
	private List<Book> books;

	/** type of operation, can be Delete or Modify */
	private String operationType;

	/** book selected from list */
	private Book selectedBook;

	/**
	 * Constructs List Books Transaction
	 * @param parentController
	 */
	public ListBooksTransaction(Controller parentController) {
		super(parentController);
		if(parentController instanceof DeleteBooksTransaction){
			operationType = "Delete";
		}else if(parentController instanceof ModifyBooksTransaction){
			operationType = "Modify";
		}else if(parentController instanceof LibrarianController){
		
		}else{
			operationType = "Select";
		}
	}

	@Override
	public void execute() {
		showView("ListBooksView");
	}

	/**
	 * Filters the books by the provided search criteria
	 * @param searchCriteria
	 */
	private void filter(final Properties searchCriteria) {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				getBooks(searchCriteria);
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.BOOK_COLLECTION, null);
			}
		}.execute();
	}

	/**
	 * Fetches books that match searchCriteria
	 * @param searchCriteria
	 */
	private void getBooks(Properties searchCriteria){
		BookCollection bookCollection = new BookCollection();
		bookCollection.findLike(searchCriteria);
		books = bookCollection.getEntities();
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.BOOK_COLLECTION)) {
			return books;
		}else if(key.equals(Key.SELECT_BOOK)){
			return selectedBook;
		}else if(key.equals(Key.OPERATION_TYPE)){
			return operationType;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.FILTER)){
			filter((Properties)value);
		}else if(key.equals(Key.SELECT_BOOK)){
			selectedBook = (Book)value;
		}else if (key.equals(Key.EXPORT_TO_EXCEL)) {
			ExcelDocument document = DocumentFactory.createExcelDocument("ListAvailableBooksDocument", this);
			document.save();
		}
		super.stateChangeRequest(key, value);
	}
}
