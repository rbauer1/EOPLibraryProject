package document;

import java.util.List;

import model.Book;
import model.BookDueDate;
import model.Borrower;
import model.Worker;
import utilities.Key;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import controller.Controller;

public class RentBooksReceipt extends Receipt {

	public RentBooksReceipt(Controller controller) {
		super("Rent Book(s)", controller);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void build() throws DocumentException {
		document.add(createActorsPanel((Borrower)controller.getState(Key.BORROWER), (Worker)controller.getState(Key.LOGGED_IN_WORKER)));
		document.add(createSubTitle("Rented Book Details"));
		document.add(createBooksTable((List<Book>)controller.getState(Key.BOOK_COLLECTION)));

		BookDueDate dueDate = (BookDueDate)controller.getState(Key.BOOK_DUE_DATE);
		Paragraph terms = createParagraph("By signing below, I acknowledge that I have recieved these items and I agree to return the books by " +
				dueDate.getState("DueDate") + ". I acknowledge that I am responsible for the replacement of those " +
				"books that are not returned by this date.");
		addEmptyLine(terms);
		document.add(terms);

		document.add(createDate("Rental Date"));

		document.add(createParagraph("Signature: ___________________________________________"));
	}
}
