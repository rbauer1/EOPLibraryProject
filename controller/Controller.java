/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package controller;

import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import java.util.HashMap;

import userinterface.MainFrame;
import userinterface.view.View;
import userinterface.view.ViewFactory;

public abstract class Controller implements IView, IModel {

	/** Registry of views that observe this controller */
	protected ModelRegistry registry;

	/** Holds Views this manages */
	protected HashMap<String, View> views;
	
	/** Main display frame */
	protected MainFrame frame;
	
	/** Controller that created this */
	protected Controller parentController;
	
	
	/**
	 * Constructs a controller as child of provided controller.
	 * This is used to create transactions.
	 * @param parentController
	 */
	protected Controller(Controller parentController) {
		this.parentController = parentController;
		this.frame = MainFrame.getInstance();
		this.views = new HashMap<String, View>();
		this.registry = new ModelRegistry(this.getClass().getSimpleName());
	}
	
	/**
	 * Constructs a controller without a parent. This would be 
	 * used to create the main agent.
	 */
	protected Controller() {
		this(null);
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
	
	/**
	 * Shows the view of the provided name.
	 * @param name
	 */
	protected void showView(String name){
		frame.swapToView(getView(name));
	}
	
	public void subscribe(String key, IView subscriber){
		registry.subscribe(key, subscriber);
	}

	
	public void unSubscribe(String key, IView subscriber){
		registry.unSubscribe(key, subscriber);
	}
	
	public void updateState(String key, Object value){
		stateChangeRequest(key, value);
	}
}
