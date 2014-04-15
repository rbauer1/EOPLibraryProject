package model;

import java.util.Properties;

public class RentalCollection extends ModelCollection<Rental> {

	private static Properties schema;

	public RentalCollection() {
		super();
	}

	@Override
	protected Properties getSchema() {
		if (schema == null) {
			schema = getSchemaInfo(getTableName());
		}
		return schema;
	}

	@Override
	protected String getTableName() {
		return Rental.TABLE_NAME;
	}

	@Override
	protected Rental createEntity(Properties persistentState) {
		return new Rental(persistentState, true);
	}

	public void findByBorrower(Borrower borrower) {
		findByKey("BorrowerID",	(String) borrower.getState(borrower.getPrimaryKey()));
	}

	public void findOutstandingByBorrower(Borrower borrower) {
		findByQuery("SELECT * FROM " + Rental.TABLE_NAME + " INNER JOIN "
				+ Book.TABLE_NAME + " ON " + Rental.TABLE_NAME + ".BookID="
				+ Book.TABLE_NAME + "." + Book.PRIMARY_KEY
				+ " WHERE CheckinDate IS NULL AND BorrowerID='"
				+ borrower.getState(borrower.getPrimaryKey()) + "' AND "
				+ Book.TABLE_NAME + ".Status ='Active'");
	}
}
