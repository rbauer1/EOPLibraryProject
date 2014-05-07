package document;

import java.lang.reflect.Constructor;

import controller.Controller;
import event.Event;

/**
 * Factory for creating documents
 */
public class DocumentFactory {

	private static final String DOCUMENT_PACKAGE = "document";

	/**
	 * @param name
	 * @param controller
	 * @return
	 */
	public static ExcelDocument createExcelDocument(String name, Controller controller){
		try {
			Class<?> clazz = Class.forName(DOCUMENT_PACKAGE + "." + name);
			Constructor<?> ctor = clazz.getConstructor(Controller.class);
			return (ExcelDocument) ctor.newInstance(controller);
		} catch (Exception e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", e.getMessage(), Event.FATAL);
		}
		throw new IllegalArgumentException("Error occurred while creating excel document");
	}

	/**
	 * @param name
	 * @param controller
	 * @return
	 */
	public static Receipt createReceipt(String name, Controller controller){
		try {
			Class<?> clazz = Class.forName(DOCUMENT_PACKAGE + "." + name);
			Constructor<?> ctor = clazz.getConstructor(Controller.class);
			return (Receipt) ctor.newInstance(controller);
		} catch (Exception e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createReceipt", e.getMessage(), Event.FATAL);
		}
		throw new IllegalArgumentException("Error occurred while creating receipt document");
	}


	/**
	 * Private constructor to prevent instantiation
	 */
	private DocumentFactory() {}

}
