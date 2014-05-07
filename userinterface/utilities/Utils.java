package userinterface.utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

public class Utils {
	
	public static void setAllSize(JComponent comp, int w, int h) {
		Dimension d = new Dimension(w, h);

		comp.setSize(d);
		comp.setMaximumSize(d);
		comp.setMinimumSize(d);
		comp.setPreferredSize(d);
	}
	
	public static void addPadding(JComponent comp, int top, int left, int bottom, int right) {
		comp.setBorder(new EmptyBorder(top, left, bottom, right) );
	}

	public static void addCompoundPadding(JComponent comp, int top, int left, int bottom, int right) {
		Border b = comp.getBorder();
		Border padd = new EmptyBorder(top, left, bottom, right);

		if (b != null) {
			comp.setBorder(new CompoundBorder(b, padd));
		} else {
			comp.setBorder(padd);
		}
	}
	
	public static void removeFocusBorder(JButton b) {
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
	}

	public static void enableEnterKey(JButton b) {
		b.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == '\n')
					((JButton)e.getSource()).doClick();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
	}
	
	public static boolean addIcon(JButton b, String iconfile, int w, int h) {
		try {
		    Image img = (new ImageIcon(iconfile)).getImage();  
		    Image temp = img.getScaledInstance(w, h,  java.awt.Image.SCALE_SMOOTH);  
		    ImageIcon icon = new ImageIcon(temp);  
		    
		    b.setIcon(icon);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	public static boolean addIcon(JButton b, String iconfile) {
		try {
		    ImageIcon icon = new ImageIcon(iconfile);
		    b.setIcon(icon);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}
	
	public static String getImagePath(String filename) {
		return "src/images/" + filename;
	}

}
