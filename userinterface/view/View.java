/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. 
 */
package userinterface.view;

import impresario.IView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.border.Border;

import userinterface.component.Label;
import userinterface.component.Panel;
import userinterface.message.MessagePanel;
import controller.Controller;

/**
 * Abstract super class for all views
 */
public abstract class View extends Panel implements IView, ActionListener {
	
	private static final long serialVersionUID = 2685375555464693343L;

	/** Controller that this view belongs to */
	protected Controller controller;
	
	/** Panel for displaying messages */
	protected MessagePanel messagePanel;

	/** GUI components **/
	// protected final Color blue = new Color ( 133, 195, 230 );
	// protected static final Color BACKGROUNG_COLOR = new Color (0x577080);
	public static final Color BACKGROUND_COLOR = new Color(0xF0F0E8);
	public static final Color BANNER_COLOR = new Color(0xE2E2D4);
	public static final Color SEPARATOR_COLOR = new Color(0x668D3C);
	public static final Color TITLE_COLOR = new Color(0x668D3C).darker();
	public static final String FONT_NAME = "Garamond";
	public static final int GENERAL_FONT_SIZE = 16;
	public static final int TITLE_FONT_SIZE = GENERAL_FONT_SIZE + 2;

	/**
	 * indicate the font for all components that will be used in the program
	 * views, i.e. JButtons, JTextFields, JLabels and etc.
	 **/
	protected static final Font GENERAL_FONT = new Font(FONT_NAME,
			Font.TYPE1_FONT, GENERAL_FONT_SIZE);

	/**
	 * indicate the font for a View's Title, will be used to format all Views
	 * Titles
	 **/
	protected static final Font TITLE_FONT = new Font(FONT_NAME, Font.BOLD,
			TITLE_FONT_SIZE);

	/** preferred empty box size, used to position components **/
	protected static final Dimension SIZE = new Dimension(200, 15);

	/** indicate preferred size of a Button **/
	protected static final Dimension SIZE_BUTTON = new Dimension(185, 25);

	/** indicate preferred size of a Combo Box **/
	protected static final Dimension SMALL_DROP_DOWN = new Dimension(85, 25);

	/** indicate preferred size of a Button **/
	protected static final Dimension SIZE_BUTTON_SMALL = new Dimension(100, 25);

	/** indicates preferred size of a Text Area **/
	protected static final Dimension SIZE_AREA = new Dimension(300, 70);

	/** indicates preferred size of a Label **/
	protected static final Dimension SIZE_LABEL = new Dimension(102, 30);

	/** indicates preferred size of a Large Label **/
	protected static final Dimension SIZE_MID_LABEL = new Dimension(153, 30);

	/** indicates preferred size of a Large Label **/
	protected static final Dimension SIZE_LARGE_LABEL = new Dimension(204, 30);

	/** indicates preferred size for a form **/
	protected static final Dimension SIZE_FORM_SPACE = new Dimension(100, 5);

	protected static final Border FORM_BORDER = BorderFactory
			.createEmptyBorder(20, 50, 20, 20);

	protected View(Controller controller, String title) {
		this.controller = controller;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		// Add title
		add(new Label(title, true));
		
		// Add Message Panel
		this.messagePanel = new MessagePanel();
		add(messagePanel);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		afterShown();		
	}
	
	/**
	 * Hook method called after view is shown.
	 * Can be used to request focus on fields.
	 */
	protected void afterShown() {
		
	}	

	public abstract void processAction(EventObject evt);

	public void actionPerformed(ActionEvent evt) {
		processAction(evt);
	}
}
