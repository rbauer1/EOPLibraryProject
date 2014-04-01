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
}
