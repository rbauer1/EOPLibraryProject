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

import javax.swing.SwingWorker;

import model.Book;
import model.BookCollection;
import utilities.Key;
import controller.Controller;
import document.DocumentFactory;
import document.ExcelDocument;

/**
 * Transaction that handles listing all available books
 */
public class ListAvailableBooksTransaction extends Transaction {

	/** list of books returned from search */
	private List<Book> books;

	/**
	 * Constructs List Books Transaction t
	 * @param parentController
	 */
	public ListAvailableBooksTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute() {
		showView("ListAvailableBooksView");
	}

	/**
	 * Retrieves Books and Updates list
	 */
	private void getBooks() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() {
				BookCollection bookCollection = new BookCollection();
				bookCollection.findAvailable();
				books = bookCollection.getEntities();
				return null;
			}

			@Override
			public void done() {
				stateChangeRequest(Key.BOOK_COLLECTION, null);
			}
		}.execute();
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.BOOK_COLLECTION)) {
			return books;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if (key.equals(Key.REFRESH_LIST)) {
			getBooks();
		}else if (key.equals(Key.EXPORT_TO_EXCEL)) {
			ExcelDocument document = DocumentFactory.createExcelDocument("ListAvailableBooksDocument", this);
			document.save();
		}
		super.stateChangeRequest(key, value);
	}
}
