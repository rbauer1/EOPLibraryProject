package document;

import java.util.List;

import model.Book;
import userinterface.view.BookTableModel;
import utilities.Key;
import controller.Controller;

public class ListRentedBooksDocument extends ExcelDocument {

	public ListRentedBooksDocument(Controller controller) {
		super(controller);
		createTitle("Rented Books");
		
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>)controller.getState(Key.BOOK_COLLECTION);
		createTable(new BookTableModel(books));
		
		createTimestamp();
	}

}
