// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Josh Swanson/Anna Migitskiy, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package userinterface;

// system imports
import impresario.ControlRegistry;
import impresario.IControl;
import impresario.IModel;
// project imports
import impresario.IView;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

//==============================================================
public abstract class View extends JPanel implements IView, IControl, 
	ActionListener, FocusListener, ListSelectionListener
{
	private static final long serialVersionUID = 2685375555464693343L;
	/**private data**/
	protected IModel myModel;
	protected ControlRegistry myRegistry;

	/**forward declaration**/
	protected abstract void processAction(EventObject evt);

	/** forward declaration **/
	protected abstract void processListSelection(EventObject evt);

	/** GUI components **/
	protected final Color blue = new Color ( 133, 195, 230 );
	//protected final Color blue =  Color.white;

	/** preferred empty box size, used to position components **/
	final Dimension size = new Dimension( 200, 15 );

	/** indicate preferred size of a Button  **/
	final Dimension sizeButton = new Dimension( 175, 25 );
	
	/** indicate preferred size of a Combo Box  **/
	final Dimension smallDropDown = new Dimension( 85, 25 );
	
	/** indicate preferred size of a Button  **/
	final Dimension sizeButtonSmall = new Dimension( 100, 25 );

	/** indicates preferred size of a Text Area  **/
	final Dimension sizeArea = new Dimension( 300, 70 );

	/** indicates preferred size of a Label   **/
	final Dimension sizeLabel = new Dimension( 102, 30 );
	
	/** indicates preferred size of a Large Label   **/
	final Dimension sizeMidLabel = new Dimension( 153, 30 );
		
	/** indicates preferred size of a Large Label   **/
	final Dimension sizeLargeLabel = new Dimension( 204, 30 );

	/** indicates preferred size for a form **/
	final Dimension sizeFormSpace = new Dimension( 100, 5 );

	/** indicate the font for a View's Title, will be used to format all Views Titles **/
	final Font myTitleFont = new Font("Arial", Font.BOLD, 13 );


	/** indicate the font for all components that will be used in the program views, i.e. JButtons, JTextFields, JLabels and etc. **/
	final Font myComponentsFont = new Font( "Arial", Font.TYPE1_FONT, 12 );
	
	final Border formBorder = BorderFactory.createEmptyBorder( 20, 50, 20, 20 );
	
	/** Class constructor **/
	public View(IModel model, String classname)
	{
		myModel = model;
		myRegistry = new ControlRegistry(classname);
	}
	
	//New Constructor, uses reflection
	public View(IModel model)
	{
		myModel = model;
		myRegistry = new ControlRegistry(model.getClass().getSimpleName());
	}

	/** process events generated from our GUI components **/
	public void actionPerformed(ActionEvent evt) 
	{
		// DEBUG: System.out.println("View.actionPerformed(): " + evt.toString());

		processAction(evt);
	}

	/** Same as hitting return in a field, fire postStateChange **/
	public void focusLost(FocusEvent evt)
	{
		// DEBUG: System.out.println("CustomerView.focusLost()");
		// ignore temporary events
		if(evt.isTemporary() == true)
			return;

		processAction(evt);
	}

	public void focusGained(FocusEvent evt)
	{
		// We don't need this for now
	}

	/** Listen the value changes (selection etc.)  **/
	public void valueChanged(ListSelectionEvent evt)
	{
		processListSelection(evt);
	}

	public void setRegistry(ControlRegistry registry)
	{
		myRegistry = registry;
	}

	/** Allow models to register for state updates **/
	public void subscribe(String key,  IModel subscriber)
	{
		myRegistry.subscribe(key, subscriber);
	}


	/** Allow models to unregister for state updates **/
	public void unSubscribe(String key, IModel subscriber)
	{
		myRegistry.unSubscribe(key, subscriber);
	}


	/**
	* This method receives a "raw" JButton and formats it to the 
	* format, that will be used for all the buttons that appear in
	* the program. Returns the formatted button to the caller.
	* The method may be called by any of the class, that 
	* extended View class.
	**/
	protected JButton formatButton ( JButton buttonToFormat )
	{

		buttonToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
		buttonToFormat.setFont( myComponentsFont );  
		buttonToFormat.setPreferredSize( sizeButton );
		buttonToFormat.setMaximumSize(  sizeButton );
		buttonToFormat.setAlignmentX( CENTER_ALIGNMENT );

		buttonToFormat.addActionListener( this );

		return buttonToFormat;

	}
	
	/**
	* JOSH ADDED FOR SMALL BUTTONS
	* This method receives a "raw" // ?\-+\n\t?\n?((?:\t+//(?: ?.*)\n\n?)*)\t// ?\-+JButton and formats it to the 
	* format, that will be used for all the buttons that appear in
	* the program. Returns the formatted button to the caller.
	* The method may be called by any of the class, that 
	* extended View class.
	**/
	protected JButton formatButtonSmall ( JButton buttonToFormat )
	{

		buttonToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
		buttonToFormat.setFont( myComponentsFont );  
		buttonToFormat.setPreferredSize( sizeButtonSmall );
		buttonToFormat.setMaximumSize(  sizeButtonSmall );
		buttonToFormat.setAlignmentX( CENTER_ALIGNMENT );

		buttonToFormat.addActionListener( this );

		return buttonToFormat;

	}

	/**
	* This method receives a String viewTitle and formats it to the 
	* format, that will be used for all the Views' titles that appear in
	* the program. Returns the formatted JPanel to the caller.
	* The method may be called by any child of View class.
	**/
	protected JPanel formatViewTitle ( String viewTitle )
	{
		JPanel container = new JPanel( );
		container.setBackground ( blue );

		JLabel viewTitleLabel = new JLabel( viewTitle );	
		viewTitleLabel.setFont( myTitleFont );
		viewTitleLabel.setForeground( Color.red.darker().darker() );
		viewTitleLabel.setAlignmentX( CENTER_ALIGNMENT );	

		container.add( Box.createRigidArea( size ));

		container.add( viewTitleLabel );

		container.add( Box.createRigidArea( size ));	

		return container;
	}

	/**
	* ANNA UPDATED ON 3.31.12: this method will take over job of formatButton
	* ANNA UPDATED ON 4.23.12: this method will perform a job of a switching 

	* The main idea is: when any child of View is created, all the components (such as
	* buttons, text fields, combo drop down boxes and tables) are sent to this method 
	* to get formatting that is consistent throughout the entire application.
	* @param is JComponent componentToFormat - any component that needs formating
	* @return JComponent componentToFormat the same component that was sent to this 
	* method, but already formatted.	
	**/
	protected JComponent formatComponent ( JComponent componentToFormat )
	{
		// all the components will be of uniform Font and Alignment
		componentToFormat.setFont( myComponentsFont );
		componentToFormat.setAlignmentX( CENTER_ALIGNMENT );

		// if the component is JLabel call the method 
		// formatLabel ( JLabel labelToFormat ), then return it
		if ( componentToFormat instanceof JLabel ) 
		{
			return formatLabel ( (JLabel) componentToFormat );
			
		}

		
		if (!( componentToFormat instanceof JLabel ) && 
			!( componentToFormat instanceof JRadioButton ))
		{
			componentToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
			componentToFormat.setPreferredSize( componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
			componentToFormat.setMaximumSize(  componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
		}

		
		// if the component is a JButton then call formatButton method 
		// to add additional formats it needs, such as action listener
		if ( componentToFormat instanceof JButton )
		{
			return formatButton ( ( JButton )componentToFormat );
		}

		return componentToFormat;
	}
	
	/**
	
	* The main idea is: when any child of View is created, all the components (such as
	* buttons, text fields, combo drop down boxes and tables) are sent to this method 
	* to get formatting that is consistent throughout the entire application.
	* @param is JComponent componentToFormat - any component that needs formating
	* @return JComponent componentToFormat the same component that was sent to this 
	* method, but already formatted.	
	**/
	protected JComponent formatComponentLarge ( JComponent componentToFormat )
	{
		// all the components will be of uniform Font and Alignment
		componentToFormat.setFont( myComponentsFont );
		componentToFormat.setAlignmentX( CENTER_ALIGNMENT );

		// if the component is JLabel call the method 
		// formatLabel ( JLabel labelToFormat ), then return it
		if ( componentToFormat instanceof JLabel ) 
		{
			return formatLargeLabel ( (JLabel) componentToFormat );
			
		}

		
		if (!( componentToFormat instanceof JLabel ) && 
			!( componentToFormat instanceof JRadioButton ))
		{
			componentToFormat.setBorder ( new LineBorder ( Color.black, 1 ) );
			componentToFormat.setPreferredSize( componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
			componentToFormat.setMaximumSize(  componentToFormat instanceof JScrollPane ? sizeArea : sizeButton );
		}

		
		// if the component is a JButton then call formatButton method 
		// to add additional formats it needs, such as action listener
		if ( componentToFormat instanceof JButton )
		{
			return formatButton ( ( JButton )componentToFormat );
		}

		return componentToFormat;
	}


	protected JLabel formatLabel ( JLabel labelToFormat )
	{
		labelToFormat.setPreferredSize( sizeLabel );
		labelToFormat.setMaximumSize(  sizeLabel );

		return labelToFormat;

	}
	

	protected JLabel formatLargeLabel ( JLabel labelToFormat )
	{
		labelToFormat.setPreferredSize( sizeMidLabel );
		labelToFormat.setMaximumSize(  sizeLargeLabel );

		return labelToFormat;

	}
	

	protected JComboBox formatDropDown ( JComboBox dropDown )
	{
			dropDown.setPreferredSize( smallDropDown );
			dropDown.setMaximumSize(  smallDropDown );		
			
			dropDown.setBorder ( new LineBorder ( Color.black, 1 ) );
			dropDown.setFont( myComponentsFont ); 
			dropDown.setAlignmentX( CENTER_ALIGNMENT );		

			return dropDown;
	}
	
	/**
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Text_Field
	**/
	protected JPanel formatCurrentPanel ( String labelName, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ) );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	
	
	/**
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Text_Field
	**/
	protected JPanel formatCurrentPanelLarge ( String labelName, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponentLarge ( currentLabel ) );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	
	
	/**
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Text_Field
	* Plus, the contents of the whole panel is centered
	**/
	protected JPanel formatCurrentPanelCenter ( String labelName, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.CENTER ));

		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ) );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	
	
	/** ADDED BY S. MITRA 2013/03/03
	*
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Text_Field
	* This sends the actual JLabel object from the client to this method
	**/
	protected JPanel formatCurrentPanel ( JLabel lbl, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( blue );
		
		lbl.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( lbl) );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	
	
	/** ADDED BY ANDREW ALLEN 2013/04/05
	*
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Text_Field
	* This sends the actual JLabel object from the client to this method
	**/
	protected JPanel formatCurrentPanelLarge ( JLabel lbl, JComponent component )
	{
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( blue );
		
		lbl.setFont( myComponentsFont );
		currentPanel.add( formatComponentLarge ( lbl) );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}	
	
	/**
	* MONEY
	**/
	protected JPanel formatMoneyPanel ( String labelName, JComponent component )
	{
		// indicates preferred size of a Label that is used in conjunction
		// with the $ money sign on the money panel
		final Dimension shortLabel = new Dimension( 90, 30 );

		
		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );
		JLabel dollarLabel = new JLabel("$");

		currentLabel.setFont( myComponentsFont );
		dollarLabel.setFont( myComponentsFont );
			
		currentLabel.setPreferredSize( shortLabel );
		currentLabel.setMaximumSize( shortLabel );
		
		currentPanel.add( currentLabel );		
		currentPanel.add( dollarLabel );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}
	

	/** ADDED BY ANDREW ALLEN 4/5/13
	 * MONEY
	**/
	protected JPanel formatMoneyPanelLarge ( String labelName, JComponent component )
	{
		// indicates preferred size of a long Label that is used in conjunction
		// with the $ money sign on the money panel
		final Dimension longLabel = new Dimension( 141, 30 );

		JPanel currentPanel = new JPanel ( );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));

		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );
		JLabel dollarLabel = new JLabel("$");

		currentLabel.setFont( myComponentsFont );
		dollarLabel.setFont( myComponentsFont );
			
		currentLabel.setPreferredSize( longLabel );
		currentLabel.setMaximumSize( longLabel );
		
		currentPanel.add( currentLabel );		
		currentPanel.add( dollarLabel );
		
		currentPanel.add( formatComponent ( component ) );
		return currentPanel;
	}
	
	
	/**
	* Method is overloaded: program will modify the appearance of the Panel 
	* based on the number of components sent to it.
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Comp_1	Comp_2	Comp_3
	**/
	protected JPanel formatCurrentPanel ( String labelName, JComponent comp_1, 
										  JComponent comp_2, JComponent comp_3 )
	{
		JPanel currentPanel = new JPanel ();
		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ));

		if ( comp_1 instanceof JTextField )
		{
			currentPanel.add( formatComponent ( comp_1 ) );
		}
		
		currentPanel.add( comp_1 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_1 ) : formatComponent ( comp_1 ) );
		currentPanel.add( comp_2 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_2 ) : formatComponent ( comp_2 ) );
		currentPanel.add( comp_3 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_3 ) : formatComponent ( comp_3 ) );
		
		return currentPanel;
	}	
	

	/**
	* Method is overloaded: program will modify the appearance of the Panel 
	* based on the number of components sent to it.
	* This method is called from any of the children of View class when there 
	* is a need for combination/layout in the form of:
	* Label:      Comp_1	Comp_2	Comp_3
	**/
	protected JPanel formatCurrentPanelLarge ( String labelName, JComponent comp_1, 
										  JComponent comp_2, JComponent comp_3 )
	{
		JPanel currentPanel = new JPanel ();
		currentPanel.setBackground ( blue );
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponentLarge ( currentLabel ));

		if ( comp_1 instanceof JTextField )
		{
			currentPanel.add( formatComponent ( comp_1 ) );
		}
		
		currentPanel.add( comp_1 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_1 ) : formatComponent ( comp_1 ) );
		currentPanel.add( comp_2 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_2 ) : formatComponent ( comp_2 ) );
		currentPanel.add( comp_3 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_3 ) : formatComponent ( comp_3 ) );
		
		return currentPanel;
	}	


	protected JPanel formatCurrentPanelLeft ( String labelName, JComponent comp_1, 
											  JComponent comp_2, JComponent comp_3 )
	{
		JPanel currentPanel = new JPanel ();
		currentPanel.setBackground ( blue );
		currentPanel.setLayout(new FlowLayout( FlowLayout.LEFT ));
		JLabel currentLabel = new JLabel ( labelName );

		currentLabel.setFont( myComponentsFont );
		currentPanel.add( formatComponent ( currentLabel ));

		if ( comp_1 instanceof JTextField )
		{
			currentPanel.add( formatComponent ( comp_1 ) );
		}
			
		currentPanel.add( comp_1 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_1 ) : formatComponent ( comp_1 ) );
		currentPanel.add( comp_2 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_2 ) : 
			(comp_2 instanceof JLabel ? formatLargeLabel((JLabel) comp_2) : formatComponent ( comp_2 ) ));
		currentPanel.add( comp_3 instanceof JComboBox ? formatDropDown ( (JComboBox) comp_3 ) : formatComponent ( comp_3 ) );
			
		return currentPanel;
	}	
		
	
	
	
	/**
	* This method is called from any of the children of View class when there 
	* is a need for a label in the middle   EX
	*                     OR                       
	**/
	protected JPanel formatMiddleLabel( String label )
	{
		JPanel currentPanel = new BluePanel();
		JLabel newLabel = new JLabel( label );
		
		newLabel.setFont( myComponentsFont );
		newLabel.setForeground( Color.red.darker() );
		//currentPanel.add(formatComponent(newLabel));
		currentPanel.add( newLabel );
		//currentPanel.setAlignmentY( LEFT_ALIGNMENT );
		
		return currentPanel;
	}
	
	

	protected void writeToFile(String fName)
	{
		
	}
	
	protected void saveToExcelFile()
	{
		// Put up JFileChooser
		// Retrieve full path name of file user selects
		// Create the file appropriately if it does not exist
		String reportsPath = System.getProperty("user.dir") + "\\reports";
		File reportsDir = new File(reportsPath);

		// if the directory does not exist, create it
		if (!reportsDir.exists())
		{
		    reportsDir.mkdir();  
		}
		
		JFileChooser chooser = new JFileChooser(reportsDir);
		
		//JFileChooser chooser = new JFileChooser("." + File.separator +
		//		"ReportFiles");

		CSVFileFilter filter = new CSVFileFilter();

		chooser.setFileFilter(filter);

		try
		{
			String fileName = "";

			int returnVal = chooser.showOpenDialog(this);

			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				File selectedFile = chooser.getSelectedFile();
				//selectedFile.mkdirs();
				fileName = selectedFile.getCanonicalPath();

				String tempName = fileName.toLowerCase();

				// Check if user specified the file type. If not, add the type.
				if(tempName.endsWith(".csv"))
				{
					writeToFile(fileName);
				}
				else
				{
					fileName += ".csv";
					writeToFile(fileName);
				}
				
				Desktop desktop = null;
				
			    // Before more Desktop API is used, first check 
			    // whether the API is supported by this particular 
			    // virtual machine (VM) on this particular host.
			    if(Desktop.isDesktopSupported()) 
			    {
			        desktop = Desktop.getDesktop();
			        
			        if(desktop.isSupported(Desktop.Action.OPEN))
			        {
			        	
			        	// Custom button text
			        	Object[] options = {"Open reports folder",
			        	                    "Open this report",
			        	                    "Do nothing, continue"};
			        	
			        	// Ask user what (s)he wants to do
			        	int n = JOptionPane.showOptionDialog(this,
			        	    "What do you want to do next?",
			        	    "Export to Excel",
			        	    JOptionPane.YES_NO_CANCEL_OPTION,
			        	    JOptionPane.QUESTION_MESSAGE,
			        	    null,
			        	    options,
			        	    options[2]);
			        	
			        	if(n == JOptionPane.YES_OPTION)
			        	{
			        		desktop.open(reportsDir);
			        	}
			        	else 
			        	if(n == JOptionPane.NO_OPTION)
			        	{
			        		File reportPath = new File(fileName);
			        		desktop.open(reportPath);
			        	}
			        }
			    }
			}
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(this, "Error in saving to file: "
					+ ex.toString());
		}
	}
}

