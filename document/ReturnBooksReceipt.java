package document;

import java.util.List;

import model.Book;
import model.Borrower;
import model.Worker;
import utilities.Key;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import controller.Controller;

public class ReturnBooksReceipt extends Receipt {

	public ReturnBooksReceipt(Controller controller) {
		super("Return Book(s)", controller);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void build() throws DocumentException {
		document.add(createActorsPanel((Borrower)controller.getState(Key.BORROWER), (Worker)controller.getState(Key.LOGGED_IN_WORKER)));
		document.add(createSubTitle("Returned Book Details"));
		document.add(createBooksTable((List<Book>)controller.getState(Key.BOOK_COLLECTION)));

		List<Book> outstandingBooks = (List<Book>)controller.getState(Key.OUTSTANDING_BOOKS);
		if(outstandingBooks!= null && !outstandingBooks.isEmpty()){
			document.add(createSubTitle("Oustanding Book Details"));
			Paragraph outstandingNotice = createParagraph("Our records indicated that you took advantage of the EOP Book Loan Program and were issued: "
					+ outstandingBooks.size() + " book(s) that have yet to be returned. You are required to return each of "
					+ "these books prior to the end of the semester. Each book must be returned to "
					+ "us or accounted for by _______________. Your ability to rent books will be "
					+ "held until these books have been accounted for. There could possibly be a hold placed on your student "
					+ "account at Brockport if you are not returning, which will prevent you from registering or "
					+ "acquiring transcripts.");
			addEmptyLine(outstandingNotice);
			document.add(outstandingNotice);

			document.add(createBooksTable(outstandingBooks));
		}

		document.add(createDate("Return Date"));
	}
}
