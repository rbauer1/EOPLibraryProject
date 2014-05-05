package document;

import java.util.List;

import model.Book;
import userinterface.view.BookTableModel;
import utilities.Key;
import controller.Controller;

public class ListAvailableBooksDocument extends ExcelDocument {

	public ListAvailableBooksDocument(Controller controller) {
		super(controller);
		createTitle("Available Books");
		
		@SuppressWarnings("unchecked")
		List<Book> books = (List<Book>)controller.getState(Key.BOOK_COLLECTION);
		createTable(new BookTableModel(books));
		
		createTimestamp();
	}

}
