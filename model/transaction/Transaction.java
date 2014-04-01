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
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;

import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;
import event.Event;

/**
 * Abstract class for all transactions in the system.
 */
public abstract class Transaction implements IView, IModel, ISlideShow {

	/** The registry of observers to this object */
	protected ModelRegistry registry;

	/** Holds Views this manages */
	protected HashMap<String, View> views;
	
	/** The main display frame */
	protected JFrame frame;
	
	//---------------------------------------------------------------------
	
	/**
	 * Constructor for Transactions
	 */
	public Transaction() {
		frame = MainFrame.getInstance();;
		views = new HashMap<String, View>();
		registry = new ModelRegistry(this.getClass().getSimpleName());
		setDependencies();
	}

	//---------------------------------------------------------------------

	protected abstract void setDependencies();	
	protected abstract View createView();
	
	//---------------------------------------------------------------------
	
	/**
	 * Begins execution of the Transaction. Uses createView 
	 * as a hook method that should be implemented in sub classes
	 */
	public void execute() {
		View currentView = createView();
		swapToView(currentView);
	}
	
	//---------------------------------------------------------------------

	/** 
	 * Register objects to receive state updates. 
	 */
	public void subscribe(String key, IView subscriber) {
		registry.subscribe(key, subscriber);
	}
	
	//---------------------------------------------------------------------

	/** 
	 * Unregister objects from state updates. 
	 */
	public void unSubscribe(String key, IView subscriber) {
		registry.unSubscribe(key, subscriber);
	}

	//---------------------------------------------------------------------
	
	/**
	 * Called when update occurs in entity this is subscribed to.
	 */
	public void updateState(String key, Object value) {
		// Every update will be handled in stateChangeRequest
		stateChangeRequest(key, value);
	}
	
	//---------------------------------------------------------------------
	
	public void swapToView(IView newView) {
		if (newView == null) {
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Missing view for display ", Event.ERROR);
			throw new NullPointerException();
		}

		if (newView instanceof JPanel) {
			// Component #2 is being accessed here because component #1 is the Logo Panel and remove it
			JPanel currentView = (JPanel) frame.getContentPane().getComponent(2);
			if (currentView != null) {
				frame.getContentPane().remove(currentView);
			}

			// add our view into the CENTER of the MainFrame
			frame.getContentPane().add((JPanel)newView, BorderLayout.CENTER);

			// pack the frame and show it
			frame.pack();

			// Place in center
			WindowPosition.placeCenter(frame);
		} else {
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Non-displayable view object sent ", Event.ERROR);
			throw new IllegalArgumentException();
		}
	}
	
	//---------------------------------------------------------------------
	
	/**
	 * Returns the view with the provided name. Creates it using the View
	 * factory if it cannot be found.
	 * @param name
	 * @return view
	 */
	public View getView(String name) {
		View view = views.get(name);
		if(view == null){
			view = ViewFactory.createView(name, this);
			views.put(name, view);
		}
		return view;
	}
}
