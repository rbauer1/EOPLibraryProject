/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package model;

import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

import userinterface.MainFrame;
import userinterface.View;
import database.Persistable;

/**
 * Super class for model objects. Handles setting up observer registry.
 */
public abstract class EntityBase extends Persistable implements IModel {
	
	/** Registry for entities interested in our events */
	protected ModelRegistry registry;

	/** Data store for fields from the db */
	protected Properties persistentState;

	/** Data store for views that belong to this */
	protected Map<String, View> views;
	
	/** Main display panel */
	protected JFrame frame;

	
	/**
	 * Constructs new entity and sets up registry.
	 */
	protected EntityBase() {
		frame = MainFrame.getInstance();
		views = new Hashtable<String, View>();

		// create a place to hold our state from the database
		persistentState = new Properties();

		// create a registry for subscribers
		registry = new ModelRegistry(this.getClass().getSimpleName());
	}

	@Override
	public void subscribe(String key, IView subscriber) {
		registry.subscribe(key, subscriber);
	}

	@Override
	public void unSubscribe(String key, IView subscriber) {
		registry.unSubscribe(key, subscriber);
	}

}
