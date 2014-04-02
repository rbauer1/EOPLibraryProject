/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package model.transaction;

import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import java.util.HashMap;

import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;

/**
 * Abstract class for all transactions in the system.
 */
public abstract class Transaction implements IView, IModel {

	/** The registry of observers to this object */
	protected ModelRegistry registry;

	/** Holds Views this manages */
	protected HashMap<String, View> views;
	
	/** The main display frame */
	protected MainFrame frame;
	
	/**
	 * Constructor for Transactions
	 */
	public Transaction() {
		frame = MainFrame.getInstance();;
		views = new HashMap<String, View>();
		registry = new ModelRegistry(this.getClass().getSimpleName());
		setDependencies();
	}

	/**
	 * Set dependencies for the model registry.
	 */
	protected abstract void setDependencies();	
	
	
	/**
	 * Creates and returns the initial view.
	 * Returns null if view-less transaction.
	 * @return initial view
	 */
	protected abstract View createView();
	
	/**
	 * Begins execution of the Transaction. Uses createView 
	 * as a hook method that should be implemented in sub classes
	 */
	public void execute() {
		View currentView = createView();
		frame.swapToView(currentView);
	}

	/** 
	 * Register objects to receive state updates. 
	 */
	public void subscribe(String key, IView subscriber) {
		registry.subscribe(key, subscriber);
	}

	/** 
	 * Unregister objects from state updates. 
	 */
	public void unSubscribe(String key, IView subscriber) {
		registry.unSubscribe(key, subscriber);
	}

	/**
	 * Called when update occurs in entity this is subscribed to.
	 */
	public void updateState(String key, Object value) {
		// Every update will be handled in stateChangeRequest
		stateChangeRequest(key, value);
	}
		
	/**
	 * Returns the view with the provided name. Creates it using the View
	 * factory if it cannot be found.
	 * @param name
	 * @return view
	 */
	protected View getView(String name) {
		View view = views.get(name);
		if(view == null){
			view = ViewFactory.createView(name, this);
			views.put(name, view);
		}
		return view;
	}
	
	protected void showView(String name){
		frame.swapToView(getView(name));
	}
}
