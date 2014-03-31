package userinterface;

import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import utilities.Key;

//======================================================================
public class AddBookView extends View
{
	private static final long serialVersionUID = -6030753682831962753L;

	private MessageView statusLog;

	private JTextField barcodeField;
	private JTextField titleField;
	private JTextField authorField;
	private JTextField publisherField;
	private JTextField yearOfPubField;
	private JTextField isbnField;
	private JTextField suggestedPriceField;
	private JButton clearButton;

	private JComboBox<String> bookConditionBox;
	private JComboBox<String> bookStatusBox;
	private JTextArea notesArea;

	private JButton submitButton;
	private JButton cancelButton;

	private ArrayList treeTypes;


	// constructor for this class -- takes a model object and
	// gathers all the components into this view
	//----------------------------------------------------------
	public AddBookView ( IModel model )
	{

		super( model, "AddBookView" );

		//setBackground ( blue );

		// set the layout for this panel
		setLayout( new BorderLayout () );

		// create our GUI components, add them to this panel
		add( createTitle(), BorderLayout.NORTH );

		add( createForm (), BorderLayout.CENTER );

		// Error message area
		add( createStatusLog("                          "), BorderLayout.SOUTH );

		// Observer pattern:
		// in order to know how to proceed and what message to display
		// subscribes to "LookupBarcode" and "SubmitNewTree" keys to receive the info needed,
		// once the processing of "LookupBarcode" and "SubmitNewTree" calls are finished
//		myModel.subscribe( "LookupBarcode", this );
//		myModel.subscribe( "SubmitNewTree", this );
	}

	//=============== 2 Main Process Events done via Observer Pattern  ====================

	//------------------------------------------------------------------------------------
	// processAction method works via Observer pattern with this (AddTreeTransactionView)
	// view's subscribers, in this case with AddTreeTransaction, which performs actual
	// job of Adding a Tree, Looking Up of Barcode and so on....
	//------------------------------------------------------------------------------------
	public void processAction( EventObject evt )
	{
		// Always clear the status log for a new action
		clearErrorMessage();

		if ( evt.getSource() == cancelButton )
		{
			// Call method in TreeTransaction via AddTreeTransaction dependency
			// ( "CancelAddTreeTransactionView", "CancelSubTransaction") to
			// display TreeTransaction menu
			myRegistry.updateSubscribers( "CancelAddBookView", null );
		}
		else if ( evt.getSource() == barcodeField )
		{
			// Call method in AddTreeTransaction via subscription
			// after minor validation is performed in barcodeFieldValidated ()
			//TODO figure out if this is needed
			if ( barcodeFieldValidated ())
			{
				barcodeField.removeActionListener( this );

				// MODEL MAPPING: TreeSeqDiag_R3 page Add a Tree says lookup (barcode)
				myRegistry.updateSubscribers( "LookupBarcode", barcodeField.getText().trim() );
			}
		}
		else if ( evt.getSource() == bookConditionBox )
		{
			if ( bookConditionBox.getSelectedIndex() == 0 )
			{
				displayErrorMessage ( "Please, select a valid Tree Type " +
						              "from the Tree Type Drop down List!" );
			}

		}
		else if ( evt.getSource() == clearButton )
		{
			// if the user needs to clear form, due to
			resetForm ();
		}
		else if ( evt.getSource() == submitButton )
		{
			// make sure that a tree type is selected
			Properties newBookProps = new Properties ();
			newBookProps.put( "Barcode", 			barcodeField.getText().trim());
			newBookProps.put( "Title", 				titleField.getText().trim());
			newBookProps.put( "Author", 			authorField.getText().trim());
			newBookProps.put( "Publisher", 			publisherField.getText().trim());
			newBookProps.put( "YearOfPublication", 	yearOfPubField.getText().trim());
			newBookProps.put( "ISBN", 				isbnField.getText().trim());
			newBookProps.put( "Condition", 			bookConditionBox.getSelectedItem());
			newBookProps.put( "SuggestedPrice", 	suggestedPriceField.getText().trim());
			newBookProps.put( "Notes", 				notesArea.getText());
			newBookProps.put( "Status", 			bookStatusBox.getSelectedItem());

			myRegistry.updateSubscribers(Key.SUBMIT_NEW_BOOK, newBookProps );
		}
	}

	//-------------------------------------------------------------------------------------
	// via Observer pattern this method is invoked and it reflects the state of the
	// process that is performed so far. This method takes care of displaying
	// messages on AddTreeTransactionView
	//-------------------------------------------------------------------------------------
	public void updateState(String key, Object value)
	{
//		TODO figure out if this is needed
		// indicates how the view state gets updated.
//		if ( key.equals( "LookupBarcode"  ))
//		{
//			// we expect the value variable to contain message "Proceed" or
//			// "Barcode is already associated with some other Tree",
//			// hence displayErrorMessage if it contains the later, else
//			// disable Barcode field and enable rest of the form
//			// via method populateFields()
//
//			if ( ((String) value).equals( "Proceed" ) )
//			{
//				enableFields();
//			}
//			else
//			{
//				displayErrorMessage ( (String) value );
//				resetForm ();
//			}
//		}
		// MODEL MAPPING: TreeSeqDiag_R3 page Add a Tree says
		// "Book Successfully Inserted message", i.e.
		// displays the message regarding the status of new Book Insertion into DB
		if ( key.equals(Key.SUBMIT_NEW_BOOK))
		{
			String message = ( String )value;

			// if an Error occurred during the Insertion display error message
			// and clear all the fields, for the user to try again
			if (( message.startsWith("ERR")) || (message.startsWith("Err") ))
			{
				displayErrorMessage ( message );
				enableFields();
			}
			// display success message and reset the entire form
			else
			{
				displayMessage( message );
				resetForm ();
			}
		}

	}

	//================== End 2 Main Process Events done via Observer Pattern =================

	//================== Back-Process of GUI/data validation, population and so on ===========

	//-------------------------------------------------------------
	//TODO almost definitely don't need this
//	private void populateTreeTypeBox ()
//	{
//		treeTypes = (ArrayList) myModel.getState ( "TreeTypeCollection" );
//
//		bookConditionBox.removeActionListener( this );
//
//		for( Object type : treeTypes )
//		{
//			// extract description of the Tree Type and load it into comboBox
//			bookConditionBox.addItem( ((Properties)type).getProperty( "TypeDescription" ) );
//
//		} //loop over all the treeTypes until the match found
//
//		bookConditionBox.addActionListener( this );
//	}

	//--------------------------------------------------------------
	private void resetForm ()
	{
		barcodeField.setText ( "" );
		barcodeField.setEnabled ( true );
		barcodeField.setFocusable( true );
		barcodeField.requestFocus( true );
		barcodeField.addActionListener( this );

		bookConditionBox.removeActionListener( this );
		bookConditionBox.setSelectedIndex( 0 );
		bookConditionBox.setEnabled ( false );

		bookStatusBox.setSelectedIndex( 0 );
		bookStatusBox.setEnabled ( false );

		submitButton.setEnabled ( false );
		clearButton.setEnabled( false );
		cancelButton.setText( "Done" );

		notesArea.setText( "" );
		notesArea.setEnabled ( false );
	}

	//-------------------------------------------------------------
	private void enableFields()
	{
		barcodeField.setEnabled ( false );

		bookConditionBox.removeActionListener( this );

		bookConditionBox.setEnabled ( true );
		bookStatusBox.setEnabled ( true );
		submitButton.setEnabled ( true );
		notesArea.setEnabled ( true );

		String typeBarcodePrefix =  barcodeField.getText().trim().substring( 0, 2 );

		for( Object type : treeTypes )
		{
			// preselect the tree type that is associated with the barcode supplied
			if ( ((Properties)type).getProperty( "BarcodePrefix" ).equals( typeBarcodePrefix ) )
			{
				bookConditionBox.setSelectedItem (((Properties)type).getProperty( "TypeDescription" ));
			}

		} //loop over all the treeTypes until the match found


		// if an invalid, (i.e. with no valid tree type) barcode was
		// scanned/entered user needs to clear form
		if ( bookConditionBox.getSelectedIndex () == 0 )
		{
			clearButton.setEnabled( true );
		}

		bookConditionBox.addActionListener( this );
	}

	//----------------------------------------------------------------
	// This method will search treeTypes Vector for
	// the Tree Type ID and returns it.
	//----------------------------------------------------------------
	private String getTreeTypeID()
	{
		String selection = (String)bookConditionBox.getSelectedItem();

		for( Object type : treeTypes )
		{
			// extract description and ID, because search is done based
			// on description and return the corresponding ID
			String description = ((Properties)type).getProperty( "TypeDescription" );
			String typeID = ((Properties)type).getProperty( "ID" );

			// if match is found - return ID
			if ( description.equals (selection))
			{
				return typeID;
			}
		} //loop over all the treeTypes until the match found

		return null;
	}

	//-----------------------------------------------------------------
	// Called when Barcode Field gains focus for the very 1st time,
	// would be used if the barcode is entered manually - here it is
	// not needed due to using barcode scanner, the focus is in
	// the barcode field all the time
	//----------------------------------------------------------------
	public void focusGained( FocusEvent evt )
	{
		// clear the field
		//barcodeField.setText( " " );

		// Always clear the status log for a new action
		//clearErrorMessage();
	}

	//-----------------------------------------------------------------
	// Called when Barcode Field looses focus
	//----------------------------------------------------------------
	public void focusLost ( FocusEvent evt )
	{

		// Always clear the status log for a new action
		// clearErrorMessage();
	}

	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

	//--------------------------------------------------------------------------
	protected void displayMessage( String message )
	{
		statusLog.displayMessage( message );
	}

	// There is no need for this b/c we don't have any tables here.
	//----------------------------------------------------------
	protected void processListSelection(EventObject evt) { }

	//-----------------------------------------------------------------------
	private boolean barcodeFieldValidated ()
	{
		String barcode = barcodeField.getText().trim();

		if ( barcode.length() > 20 )
		{
			displayErrorMessage( "The Maximum Length of Barcode is 20!" );
			barcodeField.setText ( "" );
			barcodeField.requestFocus( true );
		}
		if ( barcode.length() < 1 )
		{
			displayErrorMessage( "Barcode field can NOT be empty!" );
		}
		else
		{
			return true;
		}

		return false;
	}

	//================== End of Back-Process ===================================


	//================== GUI CREATION SECTION ===================================

	// Override the paint method to ensure we can set the focus when made visible
	//---------------------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);
		barcodeField.requestFocus( true );

	}

	//----------------------------------------------------------------
	// Create this View Title with the string specified and formatted
	// to this program standard in the superclass View
	//-----------------------------------------------------------------
	private JPanel createTitle()
	{
		return formatViewTitle ( "Add a Tree" );
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

		formPanel.add( createBarcodePanel ());
		formPanel.add( Box.createRigidArea( size ));
//		formPanel.add( createTreeTypePanel () );

		formPanel.add( Box.createRigidArea( size ));
		formPanel.add( createTreeStatusPanel ());

		formPanel.add( Box.createRigidArea( size ));
		formPanel.add( createTreeNotesPanel ());

		formPanel.add( Box.createRigidArea( size ));
		formPanel.add( createButtonsPanel ());

		formPanel.setBorder ( formBorder );
		return formPanel;

	}

	//----------------------------------------------------------------------------------
	private JPanel createBarcodePanel ()
	{
		barcodeField = new JTextField ( 16 );

		barcodeField.setText( " " );
		barcodeField.addActionListener( this );

		// create "raw" JButton and call superclass View to format
		// it to the program's standard, add it to the panel
		clearButton = new JButton( "Clear Barcode" );

		clearButton.setEnabled( false );

		JPanel barcodePanel = formatCurrentPanel ( "Barcode:", barcodeField );
		barcodePanel.add( formatButtonSmall ( clearButton ));

		return barcodePanel;
	}


	//----------------------------------------------------------------------------------------
//	private JPanel createTreeTypePanel ()
//	{
//		bookConditionBox = new JComboBox<String> ( new String [] { "-----------------------" });
//
//		bookConditionBox.setEnabled( false );
//		populateTreeTypeBox ();
//
//		return formatCurrentPanel ( "Tree Type:", bookConditionBox );
//	}

	//-----------------------------------------------------------------------------------------
	private JPanel createTreeStatusPanel ()
	{
		String[] status = { "Available", "Sold", "Damaged" };
		bookStatusBox = new JComboBox<String> ( status );
		bookStatusBox.setEnabled( false );

		return formatCurrentPanel ( "Status:", bookStatusBox );
	}

	//-----------------------------------------------------------------------------
	private JPanel createTreeNotesPanel ()
	{
		notesArea = new JTextArea ();
		JScrollPane notesPane = new JScrollPane ( notesArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS ,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		notesArea.setEnabled( false );

		return formatCurrentPanel ( "Notes:", notesPane );
	}

	//-------------------------------------------------------------
	private JPanel createButtonsPanel ()
	{
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground( blue );

		// create "raw" JButtons and call superclass View to format
		// the buttons to the program's standard, add them to the panel
		submitButton = new JButton( "Add" );
		buttonsPanel.add( formatButtonSmall ( submitButton ));
		submitButton.setEnabled( false );

		buttonsPanel.add( new JLabel ("     "));

		cancelButton = new JButton( "Return" );
		buttonsPanel.add( formatButtonSmall ( cancelButton ));
		buttonsPanel.setAlignmentX( CENTER_ALIGNMENT );

		return buttonsPanel;

	}
	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog( String initialMessage )
	{

		statusLog = new MessageView( initialMessage );

		return statusLog;
	}


}