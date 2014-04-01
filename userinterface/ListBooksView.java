package userinterface;

// system imports
// project imports
import impresario.IModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import model.Book;
import utilities.Key;

//==============================================================
public class ListBooksView extends View
{
	private static final long serialVersionUID = 3952404276228902079L;
	// GUI components
	private JTextField barcodeField;
	private JTextField author1Field;
	private JTextField isbnField;
	private JTextField titleField;
	@SuppressWarnings("rawtypes")
	private JComboBox statusList;
	private JTable bookTable;
	private DefaultTableModel emptyTable;
	private JPanel entries;
	private JPanel idPanel;

	private JButton submitButton;
	private JButton searchButton;
	private JButton cancelButton;

	// ComboBox data
	private String[] status = {"Active", "Lost", "Any"};

	// Collection to keep search result
	private ArrayList<Book> books;
	private List<Book> allBooks;

	// Selected row index to check if any row is selected
	private int selectedRowIndex;

	// Check if the search result contains anything
	private boolean emptySearchResult;

	// Operation type (to decide it's update or delete operation)
	private String operationType;

	// For showing error message
	private MessageView statusLog;

	//----------------------------------------------------------
	public ListBooksView(IModel transaction)
	{
		super(transaction, "ListBooksView");

		// Set everything for initial use
		books = new ArrayList<Book>();

		// Get the operation type
		operationType = (String) myModel.getState("OperationType");

		// For now, we have nothing and nothing is selected
		emptySearchResult = true;
		selectedRowIndex = -1;

		// set the layout for this panel
		setLayout( new BorderLayout () );

		// create our GUI components, add them to this panel
		add( createTitle(), BorderLayout.NORTH );
		add( createForm(), BorderLayout.CENTER );

		// Error message area, even if don't expect it to be used
		add( createStatusLog("                          "), BorderLayout.SOUTH );

		// STEP 0: Be sure you tell your model what keys you are interested in
//TODO		myModel.subscribe("ScoutCollectionErrorMessage", this);

		// If this is a deletion, make the status list invisible
//		if ((operationType.contains("Delete")) || (operationType.contains("Hours")))//TODO
//			statusList.setEnabled(false);

		myModel.subscribe(Key.GET_BOOK_COLLECTION, this);
	}

	// Override the paint method to ensure we can set the focus when made visible
	//-------------------------------------------------------------
	public void paint(Graphics g)
	{
		super.paint(g);

		// If something's already selected, clear the selection
		if(bookTable != null)
		{
			//scoutTable.getSelectionModel().clearSelection();
		}

		// Disable the submit button
		if(selectedRowIndex < 0)
		{
			submitButton.setEnabled(false);
		}
		else
		{
			submitButton.setEnabled(true);
		}
	}

	private JPanel createTitle(){
		return formatViewTitle ("Book Search");
	}

	//---------------------------------------------------------------------
	// Create the actual form that allows the user to input the new data
	// regarding a new tree
	//---------------------------------------------------------------------
	private JPanel createForm()
	{
		JPanel formPanel = new JPanel ();
		formPanel.setLayout( new BoxLayout ( formPanel, BoxLayout.Y_AXIS ));
		formPanel.setBackground ( blue );

		formPanel.add(createDataEntryFields());

		formPanel.add(Box.createRigidArea( size ));
		formPanel.add(createBookTable());

		formPanel.add(Box.createRigidArea( size ));
		formPanel.add(createNavigationButtons());


		formPanel.setBorder (BorderFactory.createEmptyBorder( 10, 10, 10, 10 ));

		return formPanel;
	}

	// This method creates the initial empty search result table
	//-----------------------------------------------------------------
	private JPanel createBookTable()
	{
		entries = new JPanel();
		entries.addHierarchyListener(getHierarchyListener());
		entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout( new BoxLayout ( tablePanel, BoxLayout.Y_AXIS ));

		Vector<String> columnNames = new Vector<String>();
		columnNames.add("Barcode");
		columnNames.add("Title");
		columnNames.add("Author");
		columnNames.add("Discipline");
		columnNames.add("ISBN");
		columnNames.add("Date Of Last Update");

		emptyTable = new DefaultTableModel(columnNames, 0);

		bookTable = new JTable(emptyTable);
		bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		bookTable.setPreferredScrollableViewportSize(new Dimension(900,400));
		bookTable.setAutoCreateRowSorter( true );
		bookTable.getSelectionModel().addListSelectionListener(this);

		/* Add a mouse listener to detect double-clicks if it's an
		/* update or a delete operation */
		bookTable.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if(e.getSource() == bookTable && e.getClickCount() == 2)
				{
					int selectedRow = -1;
					try
					{
						bookTable.convertRowIndexToModel(
								bookTable.getSelectedRow());
					}
					catch(Exception ex)
					{
						selectedRow = -1;
					}

					processDoubleClick(selectedRow);
				}
		   }
		});

		tablePanel.add(bookTable);

		JScrollPane scrollPane = new JScrollPane(bookTable);
		tablePanel.add(scrollPane);

		entries.add(tablePanel);

		return entries;
	}

	//-----------------------------------------------------------------
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JPanel createDataEntryFields()
	{
		JPanel entryFieldsPanel = new BluePanel();
		entryFieldsPanel.setLayout( new BoxLayout ( entryFieldsPanel, BoxLayout.Y_AXIS));

		JPanel searchInfoPanel = new BluePanel(new FlowLayout( FlowLayout.LEFT));

		JLabel searchInfo =	new JLabel("Leave all the fields empty to list all books");
		searchInfo.setFont(new Font("Arial", Font.BOLD, 16));
		searchInfo.setForeground(Color.BLUE);
		searchInfoPanel.add(searchInfo);
		searchInfoPanel.setAlignmentY(LEFT_ALIGNMENT);
		entryFieldsPanel.add(searchInfoPanel);

		idPanel = new BluePanel(new FlowLayout( FlowLayout.LEFT ));

		entryFieldsPanel.add(idPanel);

		entryFieldsPanel.add(formatMiddleLabel("----- OR -----"));

		JPanel bookInfoPanel = new BluePanel();
		bookInfoPanel.setLayout( new BoxLayout ( bookInfoPanel, BoxLayout.X_AXIS));

		JPanel leftPanel = new BluePanel();
		leftPanel.setLayout( new BoxLayout ( leftPanel, BoxLayout.Y_AXIS));
		
		barcodeField = new JTextField(16);
		leftPanel.add(formatCurrentPanel("Barcode:", barcodeField));

		author1Field = new JTextField(16);
		leftPanel.add(formatCurrentPanel("Author:", author1Field));
		
		
		JPanel rightPanel = new BluePanel();
		rightPanel.setLayout( new BoxLayout ( rightPanel, BoxLayout.Y_AXIS));
		
		titleField = new JTextField(16);
		rightPanel.add(formatCurrentPanel("Title:", titleField));
		
		isbnField = new JTextField(16);
		rightPanel.add(formatCurrentPanel("ISBN:", isbnField));

		statusList = new JComboBox(status);
		statusList.setPreferredSize(new Dimension(80, 25));
		statusList.setMaximumSize(new Dimension(80, 25));
		rightPanel.add( formatCurrentPanel ("Status:", statusList));

		bookInfoPanel.add(leftPanel);
		bookInfoPanel.add(rightPanel);
		
		entryFieldsPanel.add(bookInfoPanel);

		JPanel searchButtonPanel = new BluePanel();

		searchButton = new JButton( "Search" );
		searchButtonPanel.add(formatButtonSmall(searchButton));
		entryFieldsPanel.add(searchButtonPanel);

		return entryFieldsPanel;
	}


	// Create the navigation buttons
	//-------------------------------------------------------------
	private JPanel createNavigationButtons()
	{
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground( blue );

		//TODO this should read either Update or Delete depending on the intended user action
		submitButton = new JButton( "Submit" ); 
		buttonsPanel.add( formatButtonSmall ( submitButton ));

		cancelButton = new JButton( "Cancel" );
		buttonsPanel.add( formatButtonSmall ( cancelButton ));

		return buttonsPanel;
	}

	// Gets the TableModel values
	//-------------------------------------------------------------
	protected void getEntryTableModelValues()
	{
		books.clear();

		try
		{
			myModel.stateChangeRequest(Key.GET_BOOK_COLLECTION, getInfoFromFields());
			// Get and set the table model
			TableModel tableModel = new BookTableModel(books);
			bookTable.setModel(tableModel);
			bookTable.repaint();
		}
		catch (Exception e)
		{
			//TODO figure out what to do here / if this is necessary.
			e.printStackTrace();
		}
	}

	// Create the status log field
	//-------------------------------------------------------------
	private JPanel createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	private Properties getInfoFromFields()
	{
		Properties bookInfo = new Properties();
		bookInfo.setProperty("Barcode", barcodeField.getText());
		bookInfo.setProperty("Author1", author1Field.getText());
		bookInfo.setProperty("ISBN", isbnField.getText());
		bookInfo.setProperty("Title", 	titleField.getText());
		String status = (String)statusList.getSelectedItem();
		bookInfo.setProperty("BookStatus", (status.equals("Any")?"":status));
		return bookInfo;
	}


	// This method gets the selected row index
	//-------------------------------------------------------------
	private int isRowSelected()
	{
		try
		{
			return bookTable.convertRowIndexToModel(bookTable.getSelectedRow());
		}
		catch(Exception ex)
		{
			ex.printStackTrace(); //TODO is this method needed at all?
		}

		return -1;
	}

	// This method sends the selected scout to our model for the update screen
	//-------------------------------------------------------------
	private void proceed(int selectedRowIndex)
	{
		System.out.println(books);
		myModel.stateChangeRequest(Key.SELECT_BOOK, books.get(selectedRowIndex));
		
//		// OperationType = Update
//		if(operationType.contains("Update"))
//		{
//			myRegistry.updateSubscribers("UpdateScout", prps);
//		}
//		// OperationType = Delete
//		else
//		if(operationType.contains("Delete"))
//		{
//			// selectedPullDownIndex = 0; // Reset the scout selection
//
//			myRegistry.updateSubscribers("DeleteScout", prps);
//		}
//		else
//		if(operationType.contains("Hours"))
//		{
//			myRegistry.updateSubscribers("HoursWorkedScout", prps);
//		}
	}

	//-------------------------------------------------------------
	public void processAction(EventObject evt)
	{
		clearErrorMessage();

		if(evt.getSource() == searchButton)
		{
			//TODO Use validation here
//			if(getAndValidateData())
//			{
				// Empty the table
				bookTable.setModel(emptyTable);
				bookTable.repaint();
				getEntryTableModelValues();
				
				
//			}
//			else
//			{
				// Empty the table
//				bookTable.setModel(emptyTable);
//				bookTable.repaint();
//			}
		}else if (evt.getSource() == cancelButton){
			myModel.stateChangeRequest(Key.BACK_TO_BOOK_MENU, null);
		}else if(evt.getSource() == submitButton){
			selectedRowIndex = isRowSelected();
			if(isRowSelected() > -1){
				proceed(selectedRowIndex);
			} else if(isRowSelected() <= -1){
				displayErrorMessage("A Book must be selected to proceed to Update/Delete screens!");
			}
		}
	}

	//-------------------------------------------------------------
	private void processDoubleClick(int selectedRow)
	{
		proceed(selectedRowIndex);
	}

	//-------------------------------------------------------------
	public void processListSelection(EventObject evt)
	{
		selectedRowIndex = isRowSelected();

		// If we have a selected row, enable the submit button; disable otherwise
		if(selectedRowIndex < 0)
		{
			submitButton.setEnabled(false);
		}
		else
		{
			//clearErrorMessage();
			submitButton.setEnabled(true);
		}
	}

	//-------------------------------------------------------------
	private HierarchyListener getHierarchyListener()
	{
		HierarchyListener hierarchyListener = new HierarchyListener()
		{
			/* This implementation of @HierarchyListener lets us trigger some
			 * methods when a displayability of a component is changed.
			 * For example: When turning back from some subview of a scout
			 * search screen, this method helps us to update our table and
			 * scout list. */
			//-------------------------------------------------------------
            public void hierarchyChanged(HierarchyEvent evt)
            {
            	// If the displayability of a component is changed
        		if ((evt.getChangeFlags() & HierarchyEvent.DISPLAYABILITY_CHANGED) != 0)
        		{
        			// If the component is -somehow- made displayable
        			if (evt.getComponent().isDisplayable())
        			{
        				processHierarchyChanged(evt);
        			}
        		}
            }
        };

        return hierarchyListener;
	}

	//-------------------------------------------------------------
	public void processHierarchyChanged(HierarchyEvent evt)
	{
		if(evt.getSource() == entries)
		{
			/*if(scoutTable != null && isRowSelected() > 0)
			{
				scoutTable.clearSelection();
			}*/

			/* First update the search result table, because we need scoutVector
			 * updated to process the if statement after this one correctly. */
			if(! emptySearchResult)
			{
				getEntryTableModelValues();
				displayMessage("Number of Books Found: " + books.size());
			}
		}
	}

	/**
	 * Display message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
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
		statusLog.clearErrorMessage();
	}

	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if(key.equals(Key.GET_BOOK_COLLECTION)){
			books = (ArrayList<Book>)value;
		}
	}

}
