package model;

import java.util.Properties;

public class BookBarcodePrefixCollection extends ModelCollection<BookBarcodePrefix> {

	private static Properties schema;

	public BookBarcodePrefixCollection() {
		super();
	}

	@Override
	protected BookBarcodePrefix createEntity(Properties persistentState) {
		return new BookBarcodePrefix(persistentState, true);
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
		return BookBarcodePrefix.TABLE_NAME;
	}
}
