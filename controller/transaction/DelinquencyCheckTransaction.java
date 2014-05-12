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
import java.util.Iterator;
import java.util.List;

import model.Borrower;
import model.Rental;
import model.RentalCollection;
import utilities.DateUtil;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles listing and selecting a borrower
 */
public class DelinquencyCheckTransaction extends Transaction {

	/** list of books returned from search */
	private List<Rental> delinquentRentals;
	private List<Borrower> delinquentBorrowers;

	/** type of operation, can be Delete or Modify */
	private String operationType;

	/**
	 * Constructs List Books Transaction t
	 * 
	 * @param parentController
	 */
	public DelinquencyCheckTransaction(Controller parentController) {
		super(parentController);
		operationType = "Back";
		delinquentBorrowers = new ArrayList<Borrower>();
	}

	@Override
	public void execute() {
		showView("ListDelinquentBorrowersView");
	}

	/**
	 * Fetches books that match searchCriteria
	 * 
	 * @param searchCriteria
	 */
	private void getDelinquentBorrowers() {
		String today = DateUtil.getDate();
		String Query = "where (DueDate < '" + today	+ "') and (CheckinDate IS null);";
		RentalCollection rentalCollection = new RentalCollection();
		rentalCollection.find(Query);
		delinquentRentals = rentalCollection.getEntities();
		delinquentBorrowers.clear();
		if (!delinquentRentals.isEmpty()) {
			Iterator<Rental> iter = delinquentRentals.iterator();
			while (iter.hasNext()) {
				Rental rental = (Rental) iter.next();
				Borrower borrower = rental.getBorrower();
				if (borrower.isActive()
						&& !delinquentBorrowers.contains(borrower)) {
					String Notes = borrower.getPersistentState().getProperty(
							"Notes", "");
					Notes += "Marked Delinquent on " + today;
					borrower.setDelinquent(Notes);
					delinquentBorrowers.add(borrower);
				}
			}
		}
		stateChangeRequest(Key.DELINQUENT_BORROWERS_COLLECTION, null);
	}

	@Override
	public Object getState(String key) {
		if (key.equals(Key.DELINQUENT_BORROWERS_COLLECTION)) {
			return delinquentBorrowers;
		} else if (key.equals(Key.OPERATION_TYPE)) {
			return operationType;
		}
		return super.getState(key);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		System.out.println(key);
		if (key.equals(Key.REFRESH_LIST)) {
			getDelinquentBorrowers();
		}
		super.stateChangeRequest(key, value);
	}
}
