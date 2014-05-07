/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 *
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package userinterface;

import javax.swing.JComponent;
import userinterface.component.*;
import impresario.ISlideShow;
import impresario.IView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.Constructor;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;
import userinterface.view.panel.HeaderPanel;
import userinterface.view.panel.MenuPanel;
import userinterface.screen.LoginScreen;
import userinterface.screen.MainScreen;
import userinterface.screen.Screen;
import userinterface.utilities.Utils;
import userinterface.view.View;
import event.Event;

/**
 * The main frame for the EOP Library application. All sub-panels (Views) are
 * inside this one frame only.
 */
public class MainFrame extends JFrame implements ComponentListener, ISlideShow {

	private static final long serialVersionUID = 3974890556338206717L;

	/** Holds the only instance of this class for Singleton Pattern */
	private static MainFrame instance = null;

	/** The dimension of the frame */
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;

	private Screen screen = null;

	/**
	 * Returns the instance of the main frame. Creates one with blank title if none exists.
	 *
	 * @return instance
	 */
	public static MainFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame("");
		}
		return instance;
	}

	/**
	 * Returns the instance of the main frame. Creates one with provided title if none exists.
	 *
	 * @param title
	 * @return instance
	 */
	public static MainFrame getInstance(String title) {
		if (instance == null) {
			instance = new MainFrame(title);
		}
		return instance;
	}

	/** Holds the first size set to the frame to prevent resizing */
	private Dimension frameSize;


	/** Tells if size has been set yet */
	private boolean sizeSet;

	public Screen getScreen() {
		return screen;
	}

	/**
	 * Private constructor for the Singleton Pattern. Can only be called once.
	 *
	 * @param title
	 */
	private MainFrame(String title) {
		super(title);
		super.setLayout(new BorderLayout());
		super.setVisible(true);
		super.setResizable(false);
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	/**
	 * Prevents resize on frame
	 *
	 * @param e The Component Event generated by the resize request
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		if (!sizeSet) {
			sizeSet = true; // allow first resize
			frameSize = getSize();
		} else {
			// user tried to resize, set it back
			setSize(frameSize);
			pack();
			validate();
		}
	}

	@Override
	public void componentShown(ComponentEvent arg0) {

	}

	public void fix(){
		pack();

		Dimension windowSize = getSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		double width = Math.min(windowSize.getWidth(), screenSize.getWidth() - 50);
		double height = Math.min(windowSize.getHeight(), screenSize.getHeight() - 50);

		Dimension newSize = new Dimension();
		newSize.setSize(width, height);
		setSize(newSize);

		invalidate();
		validate();
		repaint();

		revalidate();
		repaint();
		WindowPosition.placeCenter(this);
	}

	@Override
	public void swapToView(IView newView) {
		if (newView == null) {
			new Event("MainFrame", "swapToView", "Missing view for display ", Event.ERROR);
			throw new NullPointerException();
		}

		String screenName = ((View)newView).getScreenName();
		if (screen == null || !screenName.equals(screen.getClass().getSimpleName())) {
			getContentPane().removeAll();

			if (screenName.equals("LoginScreen")) {
				screen = new LoginScreen((View)newView);
			}
			if (screenName.equals("MainScreen")) {
				System.out.println(screenName);
				System.out.println(screen.getClass().getSimpleName());
				screen = new MainScreen((View)newView);
			}
			getContentPane().add(screen);

		}

		if (newView instanceof View) {
			screen.setView((View)newView);
			((View) newView).afterShown();

			fix();
		} else {
			new Event("MainFrame", "swapToView", "Non-displayable view object sent ", Event.ERROR);
			throw new IllegalArgumentException();
		}
	}
}
