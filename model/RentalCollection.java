package model;

import java.util.Properties;

public class RentalCollection extends ModelCollection<Rental> {

	private static Properties schema;

	public RentalCollection() {
		super();
	}

	@Override
	protected Rental createEntity(Properties persistentState) {
		return new Rental(persistentState, true);
	}

	public void findByBook(Book book) {
		findByKey("BookID",	book.getPrimaryKeyValue());
	}

	public void findByBorrower(Borrower borrower) {
		findByKey("BorrowerID",	(String) borrower.getState(borrower.getPrimaryKey()));
	}

	public void findOutstanding(Book book, Borrower borrower) {
		findByQuery("SELECT * FROM " + Rental.TABLE_NAME + " INNER JOIN "
				+ Book.TABLE_NAME + " ON " + Rental.TABLE_NAME + ".BookID="
				+ Book.TABLE_NAME + "." + Book.PRIMARY_KEY
				+ " WHERE CheckinDate IS NULL AND BookID='"
				+ book.getPrimaryKeyValue() + "' AND BorrowerID='" + borrower.getPrimaryKeyValue() + "'");
	}

	public void findOutstandingByBook(Book book) {
		findByQuery("SELECT * FROM " + Rental.TABLE_NAME + " INNER JOIN "
				+ Book.TABLE_NAME + " ON " + Rental.TABLE_NAME + ".BookID="
				+ Book.TABLE_NAME + "." + Book.PRIMARY_KEY
				+ " WHERE CheckinDate IS NULL AND BookID='"
				+ book.getPrimaryKeyValue() + "'");
	}

	public void findOutstandingByBorrower(Borrower borrower) {
		findByQuery("SELECT * FROM " + Rental.TABLE_NAME + " INNER JOIN "
				+ Book.TABLE_NAME + " ON " + Rental.TABLE_NAME + ".BookID="
				+ Book.TABLE_NAME + "." + Book.PRIMARY_KEY
				+ " WHERE CheckinDate IS NULL AND BorrowerID='"
				+ borrower.getPrimaryKeyValue() + "' AND "
				+ Book.TABLE_NAME + ".Status ='Active'");
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
}
