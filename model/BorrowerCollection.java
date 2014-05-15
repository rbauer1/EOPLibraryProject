package model;

import java.util.Properties;

public class BorrowerCollection extends ModelCollection<Borrower> {

	private static Properties schema;

	public BorrowerCollection() {
		super();
	}

	@Override
	protected Borrower createEntity(Properties persistentState) {
		return new Borrower(persistentState, true);
	}

	/**
	 * select * from studentborrower S where S.BannerID in (select R.BorrowerID
	 * from rental R join book B on R.BookID = B.Barcode where R.CheckinDate is
	 * null and B.Status != 'inactive')
	 */
	public void findWithOutstandingRentals() {
		find("WHERE `" + Borrower.PRIMARY_KEY + "` IN (SELECT `BorrowerID` FROM `" 
				+ Rental.TABLE_NAME + "` JOIN `" + Book.TABLE_NAME + "` B ON `BookID` = `" 
				+ Book.PRIMARY_KEY + "` WHERE `CheckinDate` IS NULL AND `Status` = 'active');");
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
		return Borrower.TABLE_NAME;
	}
}
