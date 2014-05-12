/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package userinterface.view.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Worker;
import userinterface.MainFrame;
import userinterface.component.Panel;
import userinterface.component.flat.FButton;
import userinterface.component.flat.Icons;
import userinterface.utilities.Utils;
import userinterface.view.View;
import utilities.Key;
import controller.Controller;

/**
 * Header Panel for the main frame. Holds the eop logo and application title.
 */
public class HeaderPanel extends View {

	private static final long serialVersionUID = 437901299835092005L;

	/** Background color of the header */
	private static final Color BACKGROUND_COLOR = new Color(0xE2E2D4);

	/** Color of the bottom border of the header */
	private static final Color SEPARATOR_COLOR = new Color(0x668D3C);

	/** Font Color of the title */
	private static final Color TITLE_FONT_COLOR = new Color(0x00553D);

	/** Font of the title */
	private static final Font TITLE_FONT = new Font("Garamond", Font.BOLD, 22);

	/** Title */
	private static final String TITLE = "EOP Library System";

	/** EOP Icon Path */
	private static String EOP_ICON_LOCATION = "assets/images/EOP.png";
	
	/** Avatar Icon Path */
	private static String AVATAR_ICON_LOCATION;
	
	/** Title Path */
	private static final String TITLE_LOCATION = "assets/images/EOP_Header_Title.png";

	private static final int ICON_WIDTH_PADDING = 40;
	private static final int ICON_HEIGHT_PADDING = 9;

	private static final int WIDTH = MainFrame.WIDTH;
	private static final int HEIGHT = 90;

	private FButton logoutButton;
	private JPanel avatar;
	private JPanel text;

	/**
	 * Constructs a new HeaderPanel.
	 * Created by the main frame.
	 */
	public HeaderPanel(Controller controller) {
		super(controller);
	}

	@Override
	protected void build() {
		AVATAR_ICON_LOCATION = "assets/images/Programs/Worker" + ((((Worker)controller.getState(Key.LOGGED_IN_WORKER)).isAdmin())?"_Admin":"_Avatar")+".png";
		setLayout(new BorderLayout());
		setBorder(null);
		setBackground(BACKGROUND_COLOR);
		
		Utils.setAllSize(this, WIDTH, HEIGHT);

		logoutButton = new FButton("", Icons.LOGOUT, this, true);

		add(createMainHeader(), BorderLayout.LINE_START);
		add(createUserBox(), BorderLayout.LINE_END);
		add(createSeparator(), BorderLayout.PAGE_END);
	}

	private JPanel createMainHeader() {
		JPanel panel = new Panel(BACKGROUND_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JPanel iconPanel = new Panel(BACKGROUND_COLOR);
		ImageIcon icon = new ImageIcon (EOP_ICON_LOCATION);
		int iconWidth = icon.getIconWidth();
		int iconHeight = icon.getIconHeight();
		Dimension imageAndPad = new Dimension(iconWidth+ICON_WIDTH_PADDING,iconHeight+ICON_HEIGHT_PADDING);

		iconPanel.add(new JLabel("", icon, SwingConstants.LEFT));

		iconPanel.setPreferredSize(imageAndPad);
		iconPanel.setMaximumSize(imageAndPad);

		JPanel counterWeightPanel = new Panel(BACKGROUND_COLOR, SwingConstants.RIGHT);
		counterWeightPanel.setPreferredSize(imageAndPad);
		counterWeightPanel.setMaximumSize(imageAndPad);

		JLabel titleLabel = new JLabel("", new ImageIcon(TITLE_LOCATION), SwingConstants.CENTER);
		titleLabel.setForeground(TITLE_FONT_COLOR);
		titleLabel.setFont(TITLE_FONT);

		panel.add(iconPanel);
		panel.add(titleLabel);
		panel.add(counterWeightPanel);

		return panel;
	}

	private JPanel createSeparator() {
		return new Panel(TITLE_FONT_COLOR);
	}

	private JPanel createUserBox() {
		JPanel panel = new Panel(BACKGROUND_COLOR);

		int width = HeaderPanel.HEIGHT * 4;
		int height = HeaderPanel.HEIGHT;

		panel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));
		Utils.setAllSize(panel, width, height);

		text = new JPanel();
		avatar = new Panel();

		Utils.setAllSize(avatar, width / 4, height);
		Utils.setAllSize(text, width / 2, height);

		avatar.add(new JLabel(Utils.scaleIcon(AVATAR_ICON_LOCATION, width / 4,  height - 14)));

		text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
		text.setAlignmentY(Component.CENTER_ALIGNMENT);
		updateUserBox(getWorker());

		JPanel logout = new JPanel();
		Utils.setAllSize(logout, width / 4, width / 4);

		int size = width / 8;
		Utils.setAllSize(logoutButton, size, size);

		logoutButton.setOpaque(false);
		logoutButton.setContentAreaFilled(false);
		logoutButton.setBorderPainted(false);
		logoutButton.setIconConfig(new Icons.IconConfigHelper("LogOff_Clear.png", "LogOff_Clear_Hover.png", "LogOff_Clear_Pressed.png", size, size));
//		logoutButton.setColorConfig(null);

		logout.setLayout(new BoxLayout(logout, BoxLayout.X_AXIS));
		logout.add(Box.createHorizontalGlue());
		logout.add(logoutButton);
		logout.add(Box.createHorizontalGlue());

		panel.add(avatar);
		panel.add(text);
		panel.add(logout);

		return panel;
	}

	private Worker getWorker() {
		return (Worker)controller.getState(Key.LOGGED_IN_WORKER);
	}

	@Override
	public void processAction(Object source) {
		if (source == logoutButton) {
			controller.stateChangeRequest(Key.LOGOUT, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.WORKER)) {
			updateUserBox((Worker)value);
		}
	}

	private void updateUserBox(Worker w) {
		Properties wp = w.getPersistentState();
		text.add(new JLabel("Logged in as:"));
		text.add(new JLabel(wp.getProperty(Worker.PRIMARY_KEY)));
		text.add(new JLabel(wp.getProperty("FirstName") + " " + wp.getProperty("LastName")));
		text.add(new JLabel("Privileges: " + (w.isAdmin() ? "Admin" : "User")));
		Utils.addPadding(text, 2, 2, 2, 2);
	}
}
