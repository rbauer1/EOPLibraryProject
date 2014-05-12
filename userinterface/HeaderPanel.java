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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import userinterface.component.Panel;
import userinterface.utilities.Utils;

public class HeaderPanel extends JPanel {

	private static final long serialVersionUID = 437901299835092005L;

	/** Background color of the header */
	private static final Color BACKGROUND_COLOR = new Color(0xE2E2D4);

	/** EOP Icon Path */
	private static String EOP_ICON_LOCATION = "assets/images/EOP.png";

	/** Title Path */
	private static final String TITLE_LOCATION = "assets/images/EOP_Header_Title.png";

	private static final Color SEPARATOR_COLOR = new Color(0x00553D);

	public HeaderPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, SEPARATOR_COLOR));
		Utils.addCompoundPadding(this, 5, 0, 5, 15);
		setBackground(BACKGROUND_COLOR);

		JPanel iconPanel = new Panel(BACKGROUND_COLOR);
		ImageIcon icon = new ImageIcon (EOP_ICON_LOCATION);
		iconPanel.add(new JLabel("", icon, SwingConstants.LEFT));
		Utils.addPadding(iconPanel, 0, 20, 0, 15);
		add(iconPanel);

		ImageIcon title = new ImageIcon(TITLE_LOCATION);
		add(new JLabel("", title, SwingConstants.CENTER));
	}

}
