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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import model.Book;
import model.BookDueDate;
import model.Borrower;
import model.Rental;
import model.Worker;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;
import database.JDBCBroker;
import document.DocumentFactory;
import document.Receipt;
import exception.InvalidPrimaryKeyException;

/**
 * Transaction that handles the process of renting books
 */
public class RentBooksTransaction extends Transaction {

	/** Books that are to be rented */
	private ArrayList<Book> books;

	/** Borrower that is renting the books */
	private Borrower borrower;

	/** Transaction for listing borrowers */
	private Transaction listBorrowersTransaction;

	/** Worker that is performing the rental checkout */
	private Worker worker;

	/** Date that the rented books are due */
	private BookDueDate dueDate;

	/**
	 * Constructs Rent Books Transaction
	 * @param parentController
	 */
	public RentBooksTransaction(Controller parentController) {
		super(parentController);
	}

	/**
	 * Adds book to list of books to be rented. Verifies all the necessary requirements
	 * @param bookData
	 * @return true if book was added
	 */
	private void addBook(final Properties bookData){
		new SwingWorker<Boolean, Void>() {

			private Book book;

			@Override
			protected Boolean doInBackground() {
				try {
					book = new Book(bookData.getProperty(Book.PRIMARY_KEY, ""));
					if(!book.isActive()){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! The barcode you provided refers to a book that is no longer active."));
						return false;
					}
					if(!book.isAvailable()){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! The barcode you provided refers to a book that is already rented."));
						return false;
					}
					if(books.contains(book)){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.WARNING, "Heads up! The book was not added since it is already in the list to be rented."));
						return false;
					}
				} catch (InvalidPrimaryKeyException e) {
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! The barcode you provided is invalid. Please try again."));
					return false;
				}
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
					books.add(book);
					stateChangeRequest(Key.REFRESH_LIST, null);
				}
			}
		}.execute();
	}

	@Override
	public void execute() {
		worker = (Worker)parentController.getState(Key.WORKER);
		dueDate = new BookDueDate();
		listBorrowersTransaction  = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
		listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower who is renting books from the list below."));
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BORROWER, Key.BORROWER);
		dependencies.setProperty(Key.REFRESH_LIST, Key.BOOK_COLLECTION);
		return dependencies;
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		if(key.equals(Key.BOOK_DUE_DATE)){
			return dueDate;
		}
		if(key.equals(Key.WORKER)){
			return worker;
		}
		if(key.equals(Key.BOOK_COLLECTION)){
			return books;
		}
		if(key.equals(Key.PRINT_DOCUMENT)){
			return "test.pdf";
		}
		return super.getState(key);
	}

	/**
	 * Removes book from the list of books to be rented
	 * @param book
	 */
	private void removeBook(Book book) {
		books.remove(book);
		stateChangeRequest(Key.REFRESH_LIST, null);
	}

	/**
	 * Saves the rentals to the database and completes the transaction.
	 */
	private void rentBooks(){
		new SwingWorker<Boolean, Void>() {

			@Override
			protected Boolean doInBackground() {
				JDBCBroker.getInstance().startTransaction();
				List<Rental> rentals = new ArrayList<Rental>(books.size());
				boolean saveSuccess = true;
				for(Book book : books){
					book.reload();
					if(!book.isActive() || !book.isAvailable()){
						stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! The books have been altered since added. Please try again."));
						JDBCBroker.getInstance().rollbackTransaction();
						return false;
					}
					Rental rental = new Rental(borrower, book, worker, dueDate);
					saveSuccess &= rental.save();
					rentals.add(rental);
				}
				borrower.reload();
				if(borrower.isDelinquent()|| !borrower.isActive()){
					listBorrowersTransaction.execute();
					listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! The borrower has been altered since selected. Please try again."));
					JDBCBroker.getInstance().rollbackTransaction();
					return false;
				}
				return saveSuccess;
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
					Receipt reciept = DocumentFactory.createReceipt("RentBooksReceipt", RentBooksTransaction.this);
					reciept.save("test.pdf");
					TransactionFactory.executeTransaction(RentBooksTransaction.this, Key.EXECUTE_PRINT_PDF, Key.DISPLAY_MAIN_MENU);

					//stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
					//parentController.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The books were succesfully rented."));
				}else{
					JDBCBroker.getInstance().rollbackTransaction();
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving the rentals."));
				}
			}
		}.execute();
	}

	/**
	 * Sets the borrower who is renting books. Verifies the borrower is in good standing.
	 * @param borrower
	 */
	private void selectBorrower(Borrower borrower) {
		if(borrower.isDelinquent()){
			listBorrowersTransaction.execute();
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Error! The selected borrower is marked as deliquent and is not allowed to rent books at this time."));
			return;
		}
		if(!borrower.isActive()){
			listBorrowersTransaction.execute();
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Error! The selected borrower is no longer active and is not allowed to rent books at this time."));
			return;
		}
		this.borrower = borrower;
		books = new ArrayList<Book>();
		showView("RentBooksView");
		stateChangeRequest(Key.REFRESH_LIST, null);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			selectBorrower((Borrower)value);
		}else if(key.equals(Key.ADD_BOOK_TO_LIST)){
			addBook((Properties)value);
		}else if(key.equals(Key.REMOVE_BOOK_FROM_LIST)){
			removeBook((Book)value);
		}else if(key.equals(Key.RENT_BOOKS)){
			rentBooks();
		}else if(key.equals(Key.BACK)){
			String view = (String)value;
			if(view.equals("ListBorrowersView")){
				listBorrowersTransaction.execute();
				listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower who is renting books from the list below."));
			}else{
				showView(view);
			}
		} else if(key.equals(Key.DISPLAY_BORROWER_MENU)){
			key = Key.DISPLAY_MAIN_MENU;
		}
		super.stateChangeRequest(key, value);
	}

}
