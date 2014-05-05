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
