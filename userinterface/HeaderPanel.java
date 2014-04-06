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
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Header Panel for the main frame. Holds the eop logo and application title.
 */
public class HeaderPanel extends JPanel {
	
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
	
	/** Icon Path */
	private static final String ICON_LOCATION = "EOP.png";
	
	/**
	 * Constructs a new HeaderPanel.
	 * Created by the main frame.
	 */
	public HeaderPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(BACKGROUND_COLOR);
		setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(BACKGROUND_COLOR);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		add(panel);

		JLabel iconLabel = new JLabel("", new ImageIcon(ICON_LOCATION), SwingConstants.LEFT);
		panel.add(iconLabel);
		
		JLabel titleLabel = new JLabel(TITLE, SwingConstants.CENTER);
		titleLabel.setForeground(TITLE_FONT_COLOR);
		titleLabel.setFont(TITLE_FONT);
		panel.add(titleLabel);
		
		JPanel separator = new JPanel();
		separator.setBackground(SEPARATOR_COLOR);
		add(separator);
	}

}
