package model;

import java.util.Properties;

public class BookCollection extends ModelCollection<Book> {

	private static Properties schema;

	public BookCollection() {
		super();
	}

	@Override
	protected Book createEntity(Properties persistentState) {
		return new Book(persistentState, true);
	}

	/**
	 * Finds all active books that are not rented
	 */
	public void findAvailable(){
		find("WHERE (`Status` = 'Active') AND `" + Book.PRIMARY_KEY +
				"` NOT IN (SELECT `BookId` FROM `" + Rental.TABLE_NAME +
				"` WHERE `CheckinDate` IS NULL);");
	}

	/**
	 * Finds all active books that are currently rented.
	 */
	public void findRented(){
		find("WHERE (`Status` = 'Active') AND `" + Book.PRIMARY_KEY +
				"` IN (SELECT `BookId` FROM `" + Rental.TABLE_NAME +
				"` WHERE `CheckinDate` IS NULL);");
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
		return Book.TABLE_NAME;
	}
}
