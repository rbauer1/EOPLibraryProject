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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The main frame for the EOP Library application. All sub-panels (Views) are
 * inside this one frame only.
 */
public class MainFrame extends JFrame implements ComponentListener {

	private static final long serialVersionUID = 3974890556338206717L;
	private static final String COPYRIGHT = "Copyright (c) 2014: Department of Computer Science, The College at Brockport";

	/** Holds the only instance of this class for Singleton Pattern */
	private static MainFrame instance = null;

	/** Holds the first size set to the frame to prevent resizing */
	private Dimension frameSize;

	/** Tells if size has been set yet */
	private boolean sizeSet;

	/**
	 * Private constructor for the Singleton Pattern. Can only be called once.
	 * 
	 * @param title 
	 */
	private MainFrame(String title) {
		super(title);
		super.setLayout(new BorderLayout());

		/*
		 * This title is the logo panel that stays same for the duration of 
		 * the entire program. Component at position (0)
		 */		
		super.add(new LogoPanel(), BorderLayout.NORTH);

		/*
		 * This is the Copyright notice, that is the same for all the views,
		 * hence once it is installed into the main frame it should not be removed.
		 * This is Frame Component at position (1)
		 */
		JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		copyRightPanel.setBackground(new Color(133, 195, 230));
		copyRightPanel.add(new JLabel(COPYRIGHT));
		super.add(copyRightPanel, BorderLayout.SOUTH);

		/* 
		 * This panel is a "dummy" panel, which will be replaced by
		 * an actual panel, i.e. by a View that is displayed to the user
		 * This is Frame Component at position ( 2 ), which will be removed
		 * in swapToPanelView method and replaced with the view needed. 
		 */
		JPanel empty = new JPanel();
		empty.setBackground(Color.pink);
		super.add(empty, BorderLayout.CENTER);

		super.setVisible(true);
		super.setResizable(false);
		sizeSet = true;
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

	/**
	 * Returns the instance of the main frame. Creates one with blank title if none exists.
	 * 
	 * @return instance 
	 */
	public static JFrame getInstance() {
		if (instance == null) {
			instance = new MainFrame("");
		}
		return instance;
	}

	/**
	 * Prevents resize on frame
	 * 
	 * @param e The Component Event generated by the resize request
	 */
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
	public void componentHidden(ComponentEvent arg0) {

	}

	@Override
	public void componentMoved(ComponentEvent arg0) {

	}

	@Override
	public void componentShown(ComponentEvent arg0) {

	}
}