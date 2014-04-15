/************************************************************
	COPYRIGHT 2014 Sandeep Mitra and students:
		Riley Bauer, Matt Andre, Etienne Chabert, Charly Chevalier, Matthew Slomski, James Howe

    The College at Brockport, State University of New York. -
	  ALL RIGHTS RESERVED

	This file is the product of The College at Brockport and cannot
	be reproduced, copied, or used in any shape or form without
	the express written consent of The College at Brockport.
************************************************************/
package userinterface.view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import controller.Controller;
import event.Event;

/**
 * Factory Class for creating views.
 */
public class ViewFactory {
	
	public static final String VIEW_PACKAGE = "userinterface.view";
	
	/**
	 * Private Constructor so that this class can't be instantiated.
	 */
	private ViewFactory(){}
	
	/**
	 * Creates a view
	 * @param name - Class name
	 * @param controller
	 * @return view
	 */
	public static View createView(String name, Controller controller) {
		try {
			Class<?> clazz = Class.forName(VIEW_PACKAGE + "." + name);
			Constructor<?> ctor = clazz.getConstructor(Controller.class);
			return (View) ctor.newInstance(controller);
		} catch (ClassNotFoundException e) {
			new Event("ViewFactory", "createView", "Invalid view name: " + name, Event.FATAL);
		} catch (InstantiationException e) {
			new Event("ViewFactory", "createView", "Error occured while instantiating view.", Event.FATAL);
		} catch (IllegalAccessException e) {
			new Event("ViewFactory", "createView", "Illegal Access for provided view name.", Event.FATAL);
		} catch (NoSuchMethodException e) {
			new Event("ViewFactory", "createView", "Constructor does not exist in provided view.", Event.FATAL);
		} catch (SecurityException e) {
			new Event("ViewFactory", "createView", "Invalid security for constructor in view. ", Event.FATAL);
		} catch (IllegalArgumentException e) {
			new Event("ViewFactory", "createView", "Illegal argument pass to view constructor.", Event.FATAL);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			new Event("ViewFactory", "createView", "Exception thrown by view constructor.", Event.FATAL);
			e.printStackTrace();
		}
		throw new IllegalArgumentException("Invalid view name provided");
	}
}
