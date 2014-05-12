package userinterface.screen;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import userinterface.utilities.Utils;
import userinterface.view.View;
import userinterface.view.ViewFactory;
import userinterface.view.panel.HeaderPanel;
import userinterface.view.panel.MenuPanel;
import utilities.Key;

public class MainScreen extends Screen {

	private static final long serialVersionUID = -3542085686008802005L;

	private static final String COPYRIGHT = "Copyright (c) 2014: Department of Computer Science, The College at Brockport";

	/** The dimension of the frame */
	public static final int WIDTH = 1100;
	public static final int HEIGHT = 800;

	/** == */
	private HeaderPanel header = null;
	private MenuPanel menu = null;

	public MainScreen(View view) {
		super(view);

		setLayout(new BorderLayout());
		Utils.setAllSize(this, WIDTH, HEIGHT);

		header = (HeaderPanel)ViewFactory.createView("panel", "HeaderPanel", view.getController());
		menu = (MenuPanel)ViewFactory.createView("panel", "MenuPanel", view.getController());

		add(header, BorderLayout.NORTH);
		add(menu, BorderLayout.WEST);

		/*
		 * This is the Copyright notice, that is the same for all the views,
		 * hence once it is installed into the main frame it should not be removed.
		 * This is Frame Component at position (1)
		 */
		JPanel copyRightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		copyRightPanel.setBackground(View.BACKGROUND_COLOR);
		copyRightPanel.add(new JLabel(COPYRIGHT));
		add(copyRightPanel, BorderLayout.SOUTH);
	}

	@Override
	public void addView(View view) {
		Utils.addPadding(view, 5, 10, 5, 10);
		add(view, BorderLayout.CENTER);
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.RESET_MENU)) {
			menu.reset();
		}
	}

}
