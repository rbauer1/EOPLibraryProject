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

	@Override
	public Object getState(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		// TODO Auto-generated method stub
		
	}

}
