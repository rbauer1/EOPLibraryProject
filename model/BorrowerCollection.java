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

	public void findWithOutstandingRentals() {
		find("WHERE `" + Borrower.PRIMARY_KEY + "` IN (SELECT `BorrowerID` FROM `" 
				+ Rental.TABLE_NAME + "` WHERE `CheckinDate` IS NULL);");
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
