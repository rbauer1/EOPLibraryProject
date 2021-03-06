//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and students, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import userinterface.MainFrame;
import userinterface.WindowPosition;
import controller.LibrarianController;
import database.JDBCBroker;
import event.Event;

/**
 * The class containing the main program for the EOP Library application *
 */
public class EOPLibrary {

	private static final String PREFERRED_UI_STYLE = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/**
	 * The "main" entry point for the application. Carries out actions to
	 *  set up the application
	 */
	public static void main(String[] args){
		try{
			new EOPLibrary();
		}
		catch(Exception e){
			new Event("EOPLibrary", "EOPLibrary.main", "Unhandled Exception: " + e, Event.FATAL);
			e.printStackTrace();
		}
	}

	/** Main frame of the application */
	private final MainFrame mainFrame;

	/**
	 * 
	 */
	public EOPLibrary(){
		// System.out.println("EOP Library System");
		// See if you can set the look and feel requested, if not indicate error
		try{
			UIManager.setLookAndFeel(PREFERRED_UI_STYLE);
		}
		catch(Exception e){
			try{
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			}
			catch(Exception f){
				new Event(Event.getLeafLevelClassName(this), "EOPLibrary.<init>",
						"Unable to set look and feel", Event.ERROR);
			}
		}

		// Create the top-level container (main frame) and add contents to it.
		mainFrame = MainFrame.getInstance("EOP Library System");

		// Put in icon for window border and toolbar
		Toolkit toolKit = Toolkit.getDefaultToolkit();

		File iconFile = new File("EOP.jpg");

		if (iconFile.exists()){
			mainFrame.setIconImage(toolKit.getImage("EOP.jpg"));
		}

		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// Rollback any uncommitted work on exit!
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				JDBCBroker.getInstance().rollbackTransaction();
			}
		}));
		
		new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() {
				//Setup a connection to the db. Opening it here prevents the overhead cause on first login.
				JDBCBroker.getInstance().getConnection();
				return null;
			}
			
		}.execute();

		try{
			new LibrarianController();
		}
		catch(Exception e){
			System.err.println("EOPLibrary.<init> - could not create Librarian!");
			new Event(Event.getLeafLevelClassName(this), "EOPLibrary.<init>",
					"Unable to create Librarian object", Event.ERROR);
			e.printStackTrace();
		}

		mainFrame.pack();
		WindowPosition.placeCenter(mainFrame);
		mainFrame.setVisible(true);
	}
}
