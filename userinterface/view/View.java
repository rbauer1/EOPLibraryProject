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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import userinterface.MainFrame;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Label;
import userinterface.component.Panel;
import userinterface.component.Accordion;
import userinterface.message.MessagePanel;
import userinterface.screen.MainScreen;
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

	/** Buttons on the bottom of screen */
	protected Map<String, Button> buttons;

	public static final Color BACKGROUND_COLOR = new Color(0xF0F0E8);
	public static final Color BANNER_COLOR = new Color(0xE2E2D4);
	public static final Color SEPARATOR_COLOR = new Color(0x668D3C);
	public static final Color TITLE_COLOR = new Color(0x668D3C).darker();
	public static final String FONT_NAME = "Garamond";
	public static final int GENERAL_FONT_SIZE = 16;
	public static final int TITLE_FONT_SIZE = GENERAL_FONT_SIZE + 2;
	protected static final Font GENERAL_FONT = new Font(FONT_NAME, Font.TYPE1_FONT, GENERAL_FONT_SIZE);

	protected View(Controller controller) {
		this(controller, null, null);
	}

	protected View(Controller controller, String title) {
		this(controller, title, null);
	}

	protected View(Controller controller, String title, String[] buttonNames) {
		this.controller = controller;
		buttons = new HashMap<String, Button>();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Add title
		if (title != null) {
			add(ViewHelper.formatCenter(new Label(title, true), 0));
		}

		// Add Message Panel
		messagePanel = new MessagePanel();
		add(messagePanel);

		// Build Content
		build();

		// Create Buttons
		if(buttonNames != null){
			add(createButtons(buttonNames));
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		processAction(event.getSource());
	}

	/**
	 * Hook method called after view is shown.
	 * Can be used to request focus on fields.
	 */
	public void afterShown() {
	}

	public Controller getController() {
		return controller;
	}

	public String getScreenName() {
		return MainScreen.class.getSimpleName();
	}

	protected abstract void build();

	public Accordion toMenu() { return null; }

	protected JPanel createButtons(String[] names){
		Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
		for(String name : names){
			Button button = new Button(name, this);
			buttons.put(name, button);
			panel.add(ViewHelper.formatButton(button));
		}
		return panel;
	}

	public abstract void processAction(Object source);

	protected void subscribeToController(String...keys){
		for(String key : keys){
			controller.subscribe(key, this);
		}
	};
}
