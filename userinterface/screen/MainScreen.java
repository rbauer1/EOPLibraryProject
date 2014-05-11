package userinterface.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import userinterface.utilities.Utils;
import userinterface.view.View;
import userinterface.view.ViewFactory;
import userinterface.view.panel.HeaderPanel;
import userinterface.view.panel.MenuPanel;

public class MainScreen extends Screen {

	private static final long serialVersionUID = -3542085686008802005L;

	private static final String COPYRIGHT = "Copyright (c) 2014: Department of Computer Science, The College at Brockport";

	/** The dimension of the frame */
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;

	/** == */
	private final HeaderPanel header = null;
	private final MenuPanel menu = null;

	private final JPanel current = null;

	public MainScreen(View view) {
		super(view);

		setLayout(new BorderLayout());
		Utils.setAllSize(this, WIDTH, HEIGHT);

		add(ViewFactory.createView("panel", "HeaderPanel", view.getController()), BorderLayout.NORTH);
		//add(createHeader(), BorderLayout.NORTH);
		add(ViewFactory.createView("panel", "MenuPanel", view.getController()), BorderLayout.WEST);
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
		add(view, BorderLayout.CENTER);
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
