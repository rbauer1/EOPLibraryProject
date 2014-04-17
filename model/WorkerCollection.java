package model;

import java.util.Properties;

public class WorkerCollection extends ModelCollection<Worker> {
	
	private static Properties schema;

	public WorkerCollection() {
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
		return Worker.TABLE_NAME;
	}

	@Override
	protected Worker createEntity(Properties persistentState) {
		return new Worker(persistentState, true);
	}
}
