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

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.transaction.Transaction;
import model.transaction.TransactionFactory;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;
import utilities.Key;
import event.Event;
import exception.InvalidPrimaryKeyException;

/**
 * Overall class Librarian represents the System controller or Main Interface Agent
 */
public class Librarian implements IView, IModel, ISlideShow {

	/** The registry of observers to this object */
	private ModelRegistry registry;

	/** Holds Views this manages */
	protected HashMap<String, View> views;
	
	/** The main display frame */
	protected JFrame frame;

	protected String loginErrorMessage = "";

	//---------------------------------------------------------------------
	
	public Librarian(JFrame frame) {
		this.frame = frame;
		this.views = new HashMap<String, View>();
		this.registry = new ModelRegistry("Librarian");

		setDependencies();

		// Show the initial view
		showView("LoginView");
	}
	
	//---------------------------------------------------------------------

	/**
	 * Sets the dependencies for the observer registry
	 */
	private void setDependencies() {
		Properties dependencies = new Properties();
		//dependencies.setProperty("Login", "LoginError");
		registry.setDependencies(dependencies);
	}
	
	//---------------------------------------------------------------------

	/**
	 * Provides the value associated with the provided key.
	 * @param key that references an attribute
	 * @return value associated with the key
	 */
	public Object getState(String key) {
		if (key.equals(Key.LOGIN_ERROR)) {
			return loginErrorMessage;
		}
		throw new IllegalArgumentException("Unknown key: " + key);
	}

	//---------------------------------------------------------------------
	
	public void stateChangeRequest(String key, Object value) {
		if (key.equals(Key.LOGIN)) {
			loginErrorMessage = "";
			loginWorker((Properties) value);
			//TODO Possibly start using TO_BOOK_MENU in place of XXX_COMPLETED for Book transactions
			
		} else if (key.equals(Key.LOGIN_ERROR)) {			
			loginErrorMessage = value.toString();
		} else if (key.equals(Key.BACK_TO_MAIN_MENU)) {		
			showView("MainMenuView");
		} else if (key.equals(Key.BACK_TO_BOOK_MENU)) {		
			showView("BookMenuView");
		} else if (key.equals(Key.EXECUTE_ADD_BOOK)) {		
			TransactionFactory.executeTransaction(this, key, Key.BACK_TO_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_MODIFY_BOOK)) {	
			TransactionFactory.executeTransaction(this, key, Key.BACK_TO_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_DELETE_BOOK)) {	
			TransactionFactory.executeTransaction(this, key, Key.BACK_TO_BOOK_MENU);
		} else if (key.equals(Key.EXECUTE_RECOVER_PW)){		
			TransactionFactory.executeTransaction(this, key, Key.RECOVER_PW_COMPLETED);
		} else if (key.equals(Key.RECOVER_PW_COMPLETED)){	
			showView("LoginView");
		} else if (key.endsWith("Transaction")){			
			TransactionFactory.executeTransaction(this, key);
		} else if (key.equals(Key.LOGOUT)) {				
			showView("LoginView");
		} else if (key.equals(Key.EXIT_SYSTEM)) {			
			exitSystem();
		}
		registry.updateSubscribers(key, this);
	}
	
	//---------------------------------------------------------------------

	/**
	 * Exits from the system, and is likely to be called as a result of
	 * a click on a button labeled something like 'Exit Application'.
	 */
	protected void exitSystem() {
		System.exit(0);
	}
	
	//---------------------------------------------------------------------
	
	protected void loginWorker(Properties workerData) {
		try {
			Worker worker = new Worker(workerData.getProperty("BannerID"));
			if(!worker.validPassword(workerData.getProperty("Password"))){
				stateChangeRequest(Key.LOGIN_ERROR, "Invalid Banner Id or Password.");
			}else{
				System.out.println("login success");
				showView("MainMenuView");
			}
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.LOGIN_ERROR, "Invalid Banner Id or Password.");
		}		
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

	//TODO should be moved, possibly to MainFrame
	private void showView(String s){
		View view = views.get(s);
		if(view == null){
			view = ViewFactory.createView(s,this);
			views.put(s, view);
		}
		swapToView(view);
	}
	
	

	//---------------------------------------------------------------------
	
	public void swapToView(IView newView) {
		if (newView == null) {
			new Event(Event.getLeafLevelClassName(this), "swapToView", "Missing view for display ", Event.ERROR);
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
			new Event(Event.getLeafLevelClassName(this), "swapToView", "Non-displayable view object sent ", Event.ERROR);
			throw new IllegalArgumentException();
		}
	}

}