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
import model.BookCollection;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles listing and selecting a borrower
 */
public class ListRentalsTransaction extends Transaction {

	/** list of books returned from search */
	private List<Book> books;

	/** type of operation, can be Delete or Modify */
	private String operationType;

	/**
	 * Constructs List Books Transaction t
	 * @param parentController
	 */
	public ListRentalsTransaction(Controller parentController) {
		super(parentController);
		operationType = "Back";
        }

	@Override
	public void execute() {
		showView("ListUnavailableBooksView");
	}

	/**
	 * Fetches books that match searchCriteria
	 * @param searchCriteria
	 */
	private void getBooks(){
	String RentedQuery = "where (Status = 'Active') and Barcode IN (Select BookId from rental where CheckinDate IS NULL);";
            BookCollection bookCollection = new BookCollection();
		bookCollection.find(RentedQuery);
		books = bookCollection.getEntities();
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.BOOK_COLLECTION)) {
			return books;
		}else if(key.equals(Key.OPERATION_TYPE)){
			return operationType;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		System.out.println(key);
		if(key.equals(Key.BOOK_COLLECTION)){
			getBooks();
		}
		super.stateChangeRequest(key, value);
	}
}
