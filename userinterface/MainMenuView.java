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

public class MainMenuView extends View
{
	private static final long serialVersionUID = -4462137345508528750L;
	private JButton btnBookActions;
	private JButton btnBorrowerActions;
	private JButton btnWorkerActions;
	private JButton btnCheckinBook;
	private JButton btnCheckoutBook;
	private JButton btnLogout;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public MainMenuView(IModel clerk)
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
		btnBookActions = new JButton( "Book Actions" );
		navigationPanel.add( formatComponent ( btnBookActions ));
		navigationPanel.add( Box.createRigidArea( size ));

		btnBorrowerActions = new JButton( "Student Borrower Actions" );
		navigationPanel.add( formatComponent ( btnBorrowerActions ));
		navigationPanel.add( Box.createRigidArea( size ));

		btnWorkerActions = new JButton( "Workers Actions" );
		navigationPanel.add( formatComponent ( btnWorkerActions ));
		navigationPanel.add( Box.createRigidArea( size ));

		btnCheckinBook = new JButton( "Check in a Book" );
		navigationPanel.add( formatComponent ( btnCheckinBook ));
		navigationPanel.add( Box.createRigidArea( size ));
		
		btnCheckoutBook = new JButton( "Check out a Book" );
		navigationPanel.add( formatComponent ( btnCheckoutBook ));
		navigationPanel.add( Box.createRigidArea( size ));
		
		btnLogout = new JButton( "Logout" );
		btnLogout.setForeground( Color.red.darker().darker() );
		navigationPanel.add( formatComponent ( btnLogout ));
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
		if (evt.getSource() == btnLogout)
		{
			// Call method in Clerk to exit system
//			myRegistry.updateSubscribers( "CancelTransactionChoiceView", null );
			myModel.stateChangeRequest(Key.LOGOUT, null);
		}
		else if (evt.getSource() == btnBookActions)
		{
//			myRegistry.updateSubscribers("PerformTransView", null);
			myModel.stateChangeRequest(Key.BACK_TO_BOOK_MENU, null);
		}
		
		else if (evt.getSource() == btnBorrowerActions)
		{
			myRegistry.updateSubscribers("ListTransactionsView", null);
		}
		else if (evt.getSource() == btnWorkerActions)
		{
			myRegistry.updateSubscribers("RevenueReportView", null);
		}
		// ADDED BY MITRA 1/6/14
//		else if (evt.getSource() == btnReturnItem)
//		{
//			myModel.stateChangeRequest("ReturnItem", null);
//		}
		
		/**
		else
		if (evt.getSource() == updateScoutButton)
		{
			myModel.stateChangeRequest("OperationType", "UpdateOperation");
			myRegistry.updateSubscribers("SearchScoutView", null);
		}
		else
		if (evt.getSource() == deleteScoutButton)
		{
			myModel.stateChangeRequest("OperationType", "DeleteOperation");
			myRegistry.updateSubscribers("SearchScoutView", null);
		}
		else
		if (evt.getSource() == hoursWorkedButton)
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
