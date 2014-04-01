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
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

import userinterface.MainFrame;
import userinterface.View;
import userinterface.WindowPosition;
import database.Persistable;
import event.Event;

/**
 * Super class for model objects. Handles setting up observer registry.
 */
public abstract class EntityBase extends Persistable implements IModel,
		ISlideShow {
	
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

	public void swapToView(IView otherView) {

		if (otherView == null) {
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Missing view for display ", Event.ERROR);
			return;
		}

		if (otherView instanceof JPanel) {
			JPanel currentView = (JPanel) frame.getContentPane()
					.getComponent(0);
			// and remove it
			frame.getContentPane().remove(currentView);
			// add our view
			frame.getContentPane().add((JPanel) otherView);
			// pack the frame and show it
			frame.pack();
			// Place in center
			WindowPosition.placeCenter(frame);
		}// end of SwapToView
		else {
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Non-displayable view object sent ", Event.ERROR);
		}
	}

}
