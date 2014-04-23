package userinterface.menu;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import userinterface.menu.Menu;
import controller.Controller;
import event.Event;

public class MenuFactory {
	
public static final String MENU_PACKAGE = "userinterface.menu";
	
	/**
	 * Private Constructor so that this class can't be instantiated.
	 */
	private MenuFactory() {}
	
	/**
	 * Creates a view
	 * @param name - Class name
	 * @param controller
	 * @return view
	 */
	public static Menu createMenu(String name, Controller controller) {
		System.out.println(name);
		try {
			Class<?> clazz = Class.forName(MENU_PACKAGE + "." + name);
			Constructor<?> ctor = clazz.getConstructor(Controller.class);
			return (Menu)ctor.newInstance(controller);
		} catch (ClassNotFoundException e) {
			new Event("MenuFactory", "createMenu", "Invalid view name: " + name, Event.FATAL);
		} catch (InstantiationException e) {
			new Event("MenuFactory", "createMenu", "Error occured while instantiating menu.", Event.FATAL);
		} catch (IllegalAccessException e) {
			new Event("MenuFactory", "createMenu", "Illegal Access for provided menu name.", Event.FATAL);
		} catch (NoSuchMethodException e) {
			new Event("MenuFactory", "createMenu", "Constructor does not exist in provided menu.", Event.FATAL);
		} catch (SecurityException e) {
			new Event("MenuFactory", "createMenu", "Invalid security for constructor in menu. ", Event.FATAL);
		} catch (IllegalArgumentException e) {
			new Event("MenuFactory", "createMenu", "Illegal argument pass to menu constructor.", Event.FATAL);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			new Event("MenuFactory", "createMenu", "Exception thrown by menu constructor.", Event.FATAL);
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Invalid menu name provided");
	}

}
