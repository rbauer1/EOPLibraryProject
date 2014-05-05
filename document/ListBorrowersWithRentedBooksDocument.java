package document;

import java.util.List;

import model.Borrower;
import userinterface.view.BorrowerTableModel;
import utilities.Key;
import controller.Controller;

public class ListBorrowersWithRentedBooksDocument extends ExcelDocument {

	public ListBorrowersWithRentedBooksDocument(Controller controller) {
		super(controller);
		createTitle("Borrowers With Outstanding Books");
		
		@SuppressWarnings("unchecked")
		List<Borrower> borrowers = (List<Borrower>)controller.getState(Key.BORROWER_COLLECTION);
		createTable(new BorrowerTableModel(borrowers));
		
		createTimestamp();
	}

}
