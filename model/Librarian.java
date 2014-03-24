// tabs=4
// 
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and students 
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
// specify the package
package model;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

// system imports
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JPanel;
import event.Event;
import exception.InactiveWorkerException;

/** Overall class Librarian represents the System controller**/
/** MODEL MAPPING: Librarian - Main Interface Agent */
//==============================================================
public class Librarian implements IView, IModel, ISlideShow
{

	// Constants

	// Impresario dependencies
	private Properties dependencies;
	private ModelRegistry myRegistry;

	// Keep track of views this Controller manages
	protected Hashtable myViews;
	// Access to singleton main display frame
	protected JFrame myFrame;

	// Factory used to reduce coupling between the Controller and the views it manages
	protected ViewFactory myFactory;

	// FOR USING IMPRESARIO: Declare the State variables held in this class
	// Much of the state can be held in 'persistableState', a Properties object
	// holding data got from the database. Since this is not a Persistable object,
	// you hold the state in other instance variables, like the one below.
	//protected Worker loggedinWorker;

	// To manage updates of the managed views -- messages displayed on the views are held in these attributes
	protected String loginErrorMessage = "";

	//---------------------------------------------------------------------
	public Librarian(JFrame frm)
	{

		myFrame = frm;
		myViews = new Hashtable();

		myFactory = new ViewFactory();


		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("Librarian");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Librarian",
					"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		// MODEL MAPPING: Transition from initial state to Main Menu shown in Menu state diagram
		// NOTE: The display of the first view has to be done differently
		createAndShowLoginView();

	}

	/**
	 * Required by the Impresario framework
	 */
	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();

		dependencies.setProperty("Login", "LoginError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("LoginError") == true)
		{
			return loginErrorMessage;
		}
		/*
		else
		{
			if (loggedinWorker != null)
			{
				return loggedinWorker.getState(key);
			}
		}*/

		return null;
	}

	/**
	 * Method called from client if the full state of the object is desired - i.e.,
	 * the values associated with all fields.
	 *
	 * @return	null, since there is no 'persistent state' associated with this class.
	 */
	//----------------------------------------------------------
	public Properties getCompleteState()
	{
		return null;
	}

	//--------------------------------------------------------------------------
	//
	//----------------------------------------------------------------------------
	@SuppressWarnings("deprecation")
	public void stateChangeRequest(String key, Object value)
	{
		
		if ( key.equals( "Login" ))
		{
			loginErrorMessage = "";

			
		}
		else
		// MODEL MAPPING: Receipt of "Dismiss Window" user action shown on
		// Menu State Diagram (from Main Menu Screen to final state)
		if ( key.equals( "ExitSystem" ) )
		{
			exitSystem();
		}

		myRegistry.updateSubscribers( key, this );
	}


	/**
	 * Method called from external client (typically, a view). This method will
	 * cause an exit from the system, and is likely to be called as a result of
	 * a click on a button labeled something like 'Exit Application'.
	 */
	//-----------------------------------------------------------------------------------
	protected void exitSystem()
	{
		System.exit(0);
	}

	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}

	//-----------------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// Every update will be handled in stateChangeRequest
		stateChangeRequest(key, value);
	}

	//-----------------------------------------------------------------------------
	protected void createAndShowLoginView()
	{
		View localView = ViewFactory.createView("LoginView", this); // USE VIEW FACTORY

		// swap the contents of current frame to this view
		swapToView(localView);
	}


	
	//----------------------------------------------------------------------------
	private void swapToPanelView(JPanel otherView)
	{
		// MITRA: Component #2 is being accessed here because component #1 is the Logo Panel
		JPanel currentView = (JPanel)myFrame.getContentPane().getComponent( 2 );
		// and remove it
		if (currentView != null)
		{
			myFrame.getContentPane().remove(currentView);
		}

		// add our view into the CENTER of the MainFrame
		myFrame.getContentPane().add( otherView, BorderLayout.CENTER );

		//pack the frame and show it
		myFrame.pack();

		//Place in center
		WindowPosition.placeCenter(myFrame);
	}

	//-----------------------------------------------------------------------------
	public void swapToView( IView otherView )
	{

		if (otherView == null)
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Missing view for display ", Event.ERROR);
			return;
		}

		if (otherView instanceof JPanel)
		{
			swapToPanelView((JPanel)otherView);
		}//end of SwapToView
		else
		{
			new Event(Event.getLeafLevelClassName(this), "swapToView",
					"Non-displayable view object sent ", Event.ERROR);
		}

	}


}//end of class