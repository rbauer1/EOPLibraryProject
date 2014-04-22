package controller.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
import exception.InvalidPrimaryKeyException;

public class RentBooksTransaction extends Transaction {

	private List<Book> books;

	/** Borrower that is checking out the books */
	private Borrower borrower;

	/** Transaction for listing borrowers */
	private Transaction listBorrowersTransaction;

	private Worker worker;

	private BookDueDate dueDate;

	public RentBooksTransaction(Controller parentController) {
		super(parentController);
	}

	private boolean addBook(Properties bookData) {
		try {
			Book book = new Book(bookData.getProperty(Book.PRIMARY_KEY, ""));
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
			books.add(book);
			stateChangeRequest(Key.REFRESH_LIST, null);
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aww shucks! The barcode you provided is invalid. Please try again."));
			return false;
		}
		return true;
	}

	private void rentBooks() {
		JDBCBroker.getInstance().startTransaction();
		List<Rental> rentals = new ArrayList<Rental>(books.size());
		boolean saveSuccess = true;
		for(Book book : books){
			Rental rental = new Rental(borrower, book, worker, dueDate);
			saveSuccess &= rental.save();
			rentals.add(rental);
		}
		if(saveSuccess){
			JDBCBroker.getInstance().commitTransaction();
			//TODO print receipt
			stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
			parentController.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Good Job! The books were succesfully rented."));
		}else{
			JDBCBroker.getInstance().rollbackTransaction();
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving the rentals."));
		}
	}

	@Override
	public void execute() {
		worker = (Worker)parentController.getState(Key.WORKER);
		books = new ArrayList<Book>();
		stateChangeRequest(Key.REFRESH_LIST, null);
		dueDate = new BookDueDate();
		listBorrowersTransaction  = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BORROWER);
		listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower whom is renting books from the list below."));
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
		if(key.equals(Key.BOOK_COLLECTION)){
			return books;
		}
		return super.getState(key);
	}

	private void selectBorrower(Borrower borrower) {
		if(!borrower.isDelinquent()){
			this.borrower = borrower;
			showView("RentBooksView");
		}else{
			listBorrowersTransaction.execute();
			listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Error! The selected borrower is marked as deliquent and is not allowed to rent books at this time."));
		}

	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			selectBorrower((Borrower)value);
		}else if(key.equals(Key.ADD_BOOK_TO_LIST)){
			addBook((Properties)value);
		}else if(key.equals(Key.RENT_BOOKS)){
			rentBooks();
		}else if(key.equals(Key.BACK)){
			String view = (String)value;
			if(view.equals("ListBorrowersView")){
				listBorrowersTransaction.execute();
				listBorrowersTransaction.stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Select the borrower whom is renting books from the list below."));
			}else{
				showView(view);
			}
		} else if(key.equals(Key.DISPLAY_BORROWER_MENU)){
			key = Key.DISPLAY_MAIN_MENU;
		}
		super.stateChangeRequest(key, value);
	}

}
