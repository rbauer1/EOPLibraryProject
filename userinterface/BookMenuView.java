package userinterface;

//system imports
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JButton;

import utilities.Key;

import java.util.EventObject;

// project imports
import impresario.IModel;

public class BookMenuView extends View
{
	private static final long serialVersionUID = -5522996085044073629L;
	private JButton btnAdd;
	private JButton btnModify;
	private JButton btnDelete;
	private JButton btnProcessLostBook;
	private JButton btnListAvailable;
	private JButton btnListUnavailable;
	private JButton btnBack;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public BookMenuView(IModel clerk)
	{
		super(clerk);

		setBackground ( blue );

		// set the layout for this panel
		setLayout( new BorderLayout () );

		// create our GUI components, add them to this panel
		add( createTitle(), BorderLayout.NORTH );
		
		add( createNavigationButtons(), BorderLayout.CENTER );

		// Error message area, even if don't expect it to be used
		add( createStatusLog("                          "), BorderLayout.SOUTH );
	}
	
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
		return formatViewTitle ("Choose a Transaction Operation");
	}

	//-------------------------------------------------------------
	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel navigationPanel = new BluePanel();	

		navigationPanel.setLayout( new BoxLayout ( navigationPanel, BoxLayout.Y_AXIS ));
		navigationPanel.add( Box.createRigidArea( size ));


		// create "raw" JButtons and call superclass View to format 
		// the button to the program's standard, add them to the panel
		btnAdd = new JButton( "Add Book" );
		navigationPanel.add( formatComponent ( btnAdd ));
		navigationPanel.add( Box.createRigidArea( size ));

		btnModify = new JButton( "Modify Book" );
		navigationPanel.add( formatComponent ( btnModify ));
		navigationPanel.add( Box.createRigidArea( size ));

		btnDelete = new JButton( "Delete Book" );
		navigationPanel.add( formatComponent ( btnDelete ));
		navigationPanel.add( Box.createRigidArea( size ));

		btnProcessLostBook = new JButton( "Process Lost Book" );
		navigationPanel.add( formatComponent ( btnProcessLostBook ));
		navigationPanel.add( Box.createRigidArea( size ));
		
		btnListAvailable = new JButton( "List Available Books" );
		navigationPanel.add( formatComponent ( btnListAvailable ));
		navigationPanel.add( Box.createRigidArea( size ));
		
		btnListUnavailable = new JButton( "List Unavailable Books" );
		navigationPanel.add( formatComponent ( btnListUnavailable ));
		navigationPanel.add( Box.createRigidArea( size ));
		
		btnBack = new JButton( "Back" );
		btnBack.setForeground( Color.red.darker().darker() );
		navigationPanel.add( formatComponent ( btnBack ));
		navigationPanel.add( Box.createRigidArea( size ));

		return navigationPanel;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{
		
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	// Process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(EventObject evt)
	{
		Object src = evt.getSource();
		if (src == btnBack)
		{
			// Call method in Librarian to go back
			myModel.stateChangeRequest(Key.BACK_TO_MAIN_MENU, null);
		}
		else if (src == btnAdd)
		{
			myModel.stateChangeRequest(Key.EXECUTE_ADD_BOOK, null);
		}
		
		else if (src == btnModify)
		{
			myModel.stateChangeRequest(Key.EXECUTE_LIST_BOOKS, null);
		}
		else if (src == btnDelete)
		{
			myRegistry.updateSubscribers("RevenueReportView", null);
		}
		else if (src == btnAdd)
		{
			myRegistry.updateSubscribers("PerformTransView", null);
		}
		
		else if (src == btnModify)
		{
			myRegistry.updateSubscribers("ListTransactionsView", null);
		}
		else if (src == btnDelete)
		{
			myRegistry.updateSubscribers("RevenueReportView", null);
		}
		// ADDED BY MITRA 1/6/14
//		else if (src == btnReturnItem)
//		{
//			myModel.stateChangeRequest("ReturnItem", null);
//		}
		
		/**
		else
		if (src == updateScoutButton)
		{
			myModel.stateChangeRequest("OperationType", "UpdateOperation");
			myRegistry.updateSubscribers("SearchScoutView", null);
		}
		else
		if (src == deleteScoutButton)
		{
			myModel.stateChangeRequest("OperationType", "DeleteOperation");
			myRegistry.updateSubscribers("SearchScoutView", null);
		}
		else
		if (src == hoursWorkedButton)
		{

		}
		**/
	}
	
	/**
	 * Update methods - to update views.
	 * ??????WHY BLANK?????????????????????????????????
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value) { }

	//??????????????????????????????????????????????????????????????????
	protected void processListSelection(EventObject evt) 
	{
		// TODO Auto-generated method stub
		
	}
}
