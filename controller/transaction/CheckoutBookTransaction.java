package controller.transaction;

import java.util.Properties;

import model.Borrower;
import utilities.Key;
import controller.Controller;

public class CheckoutBookTransaction extends Transaction {

	/** Borrower that is checking out the books */
	private Borrower borrower;

	/** Transaction for listing borrowers */
	private Transaction listBorrowersTransaction;

	public CheckoutBookTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute() {
		listBorrowersTransaction  = TransactionFactory.executeTransaction(this, "ListBorrowersTransaction", Key.DISPLAY_BORROWER_MENU, Key.SELECT_BOOK);
	}

	@Override
	protected Properties getDependencies(){
		Properties dependencies = new Properties();
		dependencies.setProperty(Key.SELECT_BORROWER, Key.BORROWER);
		return dependencies;
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.BORROWER)){
			return borrower;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BORROWER)){
			borrower = (Borrower)value;
			//showView("DeleteBookView");
		}
		super.stateChangeRequest(key, value);
	}

}
