package model;

import java.util.Properties;

public class BookCollection extends ModelCollection<Book> {
	
	private static Properties schema;

	public BookCollection() {
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
		return Book.TABLE_NAME;
	}

	@Override
	protected Book createEntity(Properties persistentState) {
		return new Book(persistentState, true);
	}
}
