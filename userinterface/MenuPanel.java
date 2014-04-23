package userinterface;

import javax.swing.Timer;

import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;

import javax.swing.border.*;

import java.util.List;
import java.util.LinkedList;

import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

import userinterface.component.*;

import java.awt.event.*;

public class MenuPanel extends JPanel {
	
	/** Background color of the header */
	private static final Color BACKGROUND_COLOR = new Color(0xD1DBC6);
	
	/** Color of the bottom border of the header */
	private static final Color SEPARATOR_COLOR = new Color(0x000000);
	
	/** Font Color of the title */
	private static final Color TITLE_FONT_COLOR = new Color(0x695D54);
	
	/** Font of the title */
	private static final Font TITLE_FONT = new Font("Garamond", Font.BOLD, 15);
	
	private static final int WIDTH = MainFrame.WIDTH / 5;
	private static final int HEIGHT = MainFrame.HEIGHT;
	
	private Accordion accordion = new Accordion();
	
	public MenuPanel() {
		setLayout(new BorderLayout());
		setBackground(new Color(0xD1DBC6));
		accordion.setBackground(new Color(0xD1DBC6));

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		add(accordion, BorderLayout.PAGE_START);
	}
	
	public void clear() {
		removeAll();
		accordion = new Accordion();
		add(accordion, BorderLayout.PAGE_START);
	}
	
	public void add(JComponent comp) {
		accordion.add(comp);
	}
	
	public void add(String but, JComponent[] subs) {
		Button b = new Button(but);
		Accordion container = new Accordion();
		
		for (JComponent sub : subs) {
			container.add(sub);
		}
		accordion.add(b, container);
	}
	
	public void add(String but, JComponent container) {
		accordion.add(new Button(but), container);
	}
	
	public void add(AbstractButton but, JComponent container) {
		accordion.add(but, container);
	}
	
	public void fit() {
		accordion.fit();
	}

}
