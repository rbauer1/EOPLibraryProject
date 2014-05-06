package document;

import model.Book;
import model.Borrower;
import model.Worker;
import utilities.Key;

import com.itextpdf.text.DocumentException;

import controller.Controller;

public class LostBookReceipt extends Receipt {

	public LostBookReceipt(Controller controller) {
		super("Lost Book", controller);
	}

	@Override
	protected void build() throws DocumentException {
		document.add(createActorsPanel((Borrower)controller.getState(Key.BORROWER), (Worker)controller.getState(Key.WORKER)));
		document.add(createSubTitle("Lost Book Details"));
		document.add(createBookPanel((Book)controller.getState(Key.BOOK)));
	}
}
