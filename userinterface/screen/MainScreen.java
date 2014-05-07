package userinterface.screen;

import impresario.IView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Worker;
import userinterface.MainFrame;
import userinterface.component.Panel;
import userinterface.component.flat.FButton;
import userinterface.component.flat.Icons;
import userinterface.menu.MButton;
import userinterface.view.panel.HeaderPanel;
import userinterface.view.panel.MenuPanel;
import userinterface.utilities.Utils;
import userinterface.view.View;
import userinterface.view.ViewFactory;
import utilities.Key;
import controller.Controller;

public class MainScreen extends Screen {
	
	private static final String COPYRIGHT = "Copyright (c) 2014: Department of Computer Science, The College at Brockport";
	
	/** The dimension of the frame */
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;
	
	/** == */
	private HeaderPanel header = null;
	private MenuPanel menu = null;
	
	private JPanel current = null;
	
	public MainScreen(View view) {
		super(view);
		
		setLayout(new BorderLayout());
		Utils.setAllSize(this, WIDTH, HEIGHT);
		
		add(ViewFactory.createView("panel.HeaderPanel", view.getController()), BorderLayout.NORTH);
		//add(createHeader(), BorderLayout.NORTH);
		add(ViewFactory.createView("panel.MenuPanel", view.getController()), BorderLayout.WEST);
		//add(createMenu(), BorderLayout.WEST);

		/*
		 * This is the Copyright notice, that is the same for all the views,
		 * hence once it is installed into the main frame it should not be removed.
		 * This is Frame Component at position (1)
		 */
		JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		copyRightPanel.setBackground(View.BACKGROUND_COLOR);
		copyRightPanel.add(new JLabel(COPYRIGHT));
		add(copyRightPanel, BorderLayout.SOUTH);

		/*
		 * This panel is a "dummy" panel, which will be replaced by
		 * an actual panel, i.e. by a View that is displayed to the user
		 * This is Frame Component at position ( 2 ), which will be removed
		 * in swapToPanelView method and replaced with the view needed.
		 */
		JPanel empty = new JPanel();
		empty.setBackground(Color.pink);
		add(empty, BorderLayout.CENTER);
	}
	
	@Override
	public void addView(View view) {
		// add our view into the CENTER of the MainFrame
		add((JPanel)view, BorderLayout.CENTER);
	}
	
	@Override
	public void clearView() {
		// Component #3 is being accessed here because component #0 is header, 1 is menu, 2 is menu padding.
		JPanel currentView = (JPanel) getComponent(3); //TODO figure out a way to make this less terrible. (get rid of literal)
		if (currentView != null) {
			remove(currentView);
		}
	}

	@Override
	public void processAction(Object source) {
		// TODO Auto-generated method stub
		
	}

}
