package userinterface;

import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.TableModel;


//==================================================================
public class ListBooksView extends View
{
	private static final long serialVersionUID = -129361777015448211L;

	private MessageView statusLog;

	private MessageView countLog;

	private JTable bookTable;
	private JPanel bookTablePanel;

	private JScrollPane scrollPane;

	private JButton searchButton;
	private JButton cancelButton;
	private JButton saveExcel;

	private ArrayList bookArrayList;

	private int treeCount = 0;

	public ListBooksView ( IModel transaction )
	{

		super( transaction, "ListSoldTreeTransactionView" );

		bookArrayList = new ArrayList ();
		setLayout( new BorderLayout () );

		add( createTitle(), BorderLayout.NORTH );
		add( createForm (), BorderLayout.CENTER );

		add( createStatusLog("                          "), BorderLayout.SOUTH );

		myModel.subscribe( "SearchSoldTrees", this );


	}

	//====================== 2 Main Processes done via Observer Pattern  =========================

	//----------------------------------------------------------------------------------------
	// processAction method works via Observer pattern with this (UpdateTreeTypeTransactionView)
	// view's subscribers, in this case with UpdateTreeTypeTransaction, which performs actual
	// job of Updating a Tree Type after non duplicate entry is verified
	//----------------------------------------------------------------------------------------
	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction( EventObject evt )
	{
		// Always clear the status log for a new action
		clearErrorMessage();

		if ( evt.getSource() == cancelButton )
		{
			myRegistry.updateSubscribers( "CancelListSoldTreeTransactionView", null );
		}
		else if ( evt.getSource() == searchButton )
		{
			bookTablePanel.removeAll();
			bookTablePanel.repaint();

//TODO			myRegistry.updateSubscribers( "SearchSoldTrees", getTreeTypeID() );

		}
		else if( evt.getSource() == saveExcel )
		{
			saveToExcelFile();
		}

	}

	/**
	 * Update methods - to update views.
	 */
	public void updateState(String key, Object value)
	{
		if ( key.equals ("SearchSoldTrees"))
		{
			bookArrayList = (ArrayList)value;

			populateTable();

			cancelButton.setText( "Done" );
		}

	}

	private void populateTable ()
	{
		treeCount = bookArrayList.size();

		if ( treeCount > 0 )
		{

			bookTable.setModel( new BookTableModel( bookArrayList ));
			bookTablePanel.add( scrollPane );
			saveExcel.setEnabled( true );

		}

		displayMessage ( "" + treeCount + " TREE(S) MATCHING THE CRITERIA" );


	}

		protected void writeToFile(String fName)
		{
			Vector allColumnNames = new Vector();

		    // System.out.println("Writing to file: "+fName);
	    	 try
	         {
	             FileWriter outFile = new FileWriter(fName);
	             PrintWriter out = new PrintWriter(outFile);

	             if ((bookArrayList == null) || (bookArrayList.size() == 0)){
	            	 out.close();
	            	 return;
	             }
	             
	             Properties firstElement = (Properties)bookArrayList.get(0);
	             Enumeration allColumns = firstElement.keys();
	             String line = "";
	             while (allColumns.hasMoreElements())
	             {
	            	 String nextColumn = (String)allColumns.nextElement();
	            	 allColumnNames.addElement(nextColumn);
	            	 line += nextColumn + ", ";
	             }
	             int lastIndexOfComma = line.lastIndexOf(", ");
	             line = line.substring(0, lastIndexOfComma);
	             out.println(line);

	             for (int k = 0; k < bookArrayList.size(); k++)
	             {
	                 String valuesLine = "";
	                 Properties nextRow = (Properties)bookArrayList.get(k);

	                 for (int j = 0; j < allColumnNames.size()-1; j++)
	                 {
	                	 String nextValue = nextRow.getProperty((String)allColumnNames.elementAt(j));

	                	 valuesLine += nextValue + ", ";
	                 }
	                 valuesLine += nextRow.getProperty((String)allColumnNames.elementAt(allColumnNames.size()-1));
	                 out.println(valuesLine);

	             }

	             // Finally, print the time-stamp
	 			DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	 			DateFormat timeFormat = new SimpleDateFormat("hh:mm aaa");
	 			Date date = new Date();
	 			String timeStamp = dateFormat.format(date) + " " +
	 							   timeFormat.format(date);

	 			out.println("Report created on " + timeStamp);

	             out.close();
	             // Acknowledge successful completion to user with JOptionPane
	 			JOptionPane.showMessageDialog( this, "Report data saved successfully to selected file");

	         }
	    	 catch (FileNotFoundException e)
	 		 {
	 			JOptionPane.showMessageDialog(this, "Could not access file to save: "
	 					+ fName, "Save Error", JOptionPane.ERROR_MESSAGE );
	 		 }
	         catch ( IOException exception )
	         {
	        	JOptionPane.showMessageDialog( this, "Error in saving to file: " + exception.toString(), "Save Error", JOptionPane.ERROR_MESSAGE );
	         	//e.printStackTrace();
	         }
		}


		//====================================== End of Back-Process =========================================


		//================================== GUI CREATION SECTION ============================================


	// Override the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);

	}

	//----------------------------------------------------------------
	// Create this View Title with the string specified and formatted
	// to this program standard in the superclass View
	//-----------------------------------------------------------------
	private JPanel createTitle()
	{
		return formatViewTitle ( "List Sold Trees" );
	}

	//---------------------------------------------------------------------
	// Create the actual form that allows the user to input the new data
	// regarding a new tree
	//---------------------------------------------------------------------
	private JPanel createForm ()
	{
		JPanel formPanel = new JPanel ();
		formPanel.setLayout( new BoxLayout ( formPanel, BoxLayout.Y_AXIS ));
		formPanel.setBackground ( blue );

		formPanel.add( Box.createRigidArea( size ));

		formPanel.add( Box.createRigidArea( size ));

		formPanel.add( createCountLog("                          ") );

		formPanel.add( createBookTablePanel ());

		formPanel.add( Box.createRigidArea( size ));
		formPanel.add( createButtonsPanel ());

		//formPanel.setBorder ( formBorder );
		return formPanel;

	}

	//---------------------------------------------------------------------------------
	private JPanel createBookTablePanel ()
	{
		bookTablePanel = new BluePanel();
		bookTablePanel.setLayout( new FlowLayout( FlowLayout.CENTER ));

		bookTablePanel.setMinimumSize( new Dimension( 700, 150 ) );
		bookTablePanel.setMaximumSize( new Dimension( 700, 200 ) );
		bookTablePanel.setPreferredSize( new Dimension( 700, 150 ) );

		TableModel myData = new BookTableModel ( bookArrayList );

		bookTable = new JTable( myData );

		bookTable.getTableHeader().setFont ( myComponentsFont );
		bookTable.getTableHeader().setAlignmentX( CENTER_ALIGNMENT );
		bookTable.setAutoCreateRowSorter( true );

		scrollPane = new JScrollPane( bookTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );

		scrollPane.setPreferredSize( new Dimension ( 675, 125 ) );
		//scrollPane.setMinimumSize( new Dimension ( 450, 100 ) );
		//scrollPane.setMaximumSize( new Dimension ( 450, 100 ) );

		scrollPane.setBorder(BorderFactory.createLineBorder( Color.black, 1 ));

		return bookTablePanel;

	}

	//----------------------------------------------------------------------------------------
	private JPanel createButtonsPanel ()
	{
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground( blue );

		// create "raw" JButtons and call superclass View to format
		// the buttons to the program's standard, add them to the panel
		saveExcel  = new JButton( "Save to Excel" );
		buttonsPanel.add( formatButtonSmall ( saveExcel ));
		saveExcel.setEnabled( false );

		buttonsPanel.add( new JLabel ("     "));


		searchButton = new JButton( "Search" );
		buttonsPanel.add( formatButtonSmall ( searchButton ));

		buttonsPanel.add( new JLabel ("     "));

		cancelButton = new JButton( "Return" );
		buttonsPanel.add( formatButtonSmall ( cancelButton ));

		return buttonsPanel;

	}


	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog( String initialMessage )
	{

		statusLog = new MessageView( initialMessage );

		return statusLog;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createCountLog( String initialMessage )
	{

		countLog = new MessageView( initialMessage );
		countLog.setLayout( new FlowLayout( FlowLayout.CENTER ));

		return countLog;
	}


	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		countLog.clearErrorMessage();
	}

	@Override
	protected void processListSelection(EventObject evt) {
		// TODO Auto-generated method stub

	}

	//--------------------------------------------------------------------------
	protected void displayMessage( String message )
	{
		countLog.displayMessage( message );
	}


}


