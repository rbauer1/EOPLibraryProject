package document;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import controller.Controller;
import event.Event;

/**
 * Factory for creating documents
 */
public class DocumentFactory {
	
	private static final String DOCUMENT_PACKAGE = "document";

	/**
	 * Private constructor to prevent instantiation
	 */
	private DocumentFactory() {}
	
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Invalid document name: " + name, Event.FATAL);
		} catch (InstantiationException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Error occured while instantiating document.", Event.FATAL);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Illegal Access for provided view name.", Event.FATAL);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Constructor does not exist in provided document.", Event.FATAL);
		} catch (SecurityException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Invalid security for constructor in document. ", Event.FATAL);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Illegal argument pass to document constructor.", Event.FATAL);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			new Event("DocumentFactory", "createExcelDocument", "Exception thrown by document constructor.", Event.FATAL);
		}
		throw new IllegalArgumentException("Invalid document name provided");
	}

}
