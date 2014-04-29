package userinterface.component.flat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import userinterface.MainFrame;
import userinterface.component.Panel;
import userinterface.utilities.ButtonSet;
import userinterface.utilities.Utils;

public class FButton extends JButton {

	private static class AListener implements ActionListener {
		private FButton ref;
		
		public AListener(FButton b) {
			ref = b;
		}

		public void actionPerformed(ActionEvent evt) {
			ref.toggle();
		}
	}
	
	private static class MListener implements MouseListener {
		private FButton ref;

		public MListener(FButton b) {
			ref = b;
		}

	    public void mousePressed(MouseEvent e) {
	    }

	    public void mouseReleased(MouseEvent e) {
	    }

	    public void mouseEntered(MouseEvent e) {
	    	ref.rollover = true;
	    }

	    public void mouseExited(MouseEvent e) {
	    	ref.rollover = false;
	    }

	    public void mouseClicked(MouseEvent e) {
	    }
	}
	
	public void toggle() {
		System.out.println("toggle " + pressed);
		if (pressed) {
			unpress();
		} else {
			press();
		}
	}
	
	private static final Font FONT = new Font("Arial", Font.TYPE1_FONT, 14);
	private static final Color FONT_COLOR = new Color(0xF0F0E8);

	private static final Color UNCLICKED_COLOR = new Color(0x1ABC9C);
	private static final Color PRESSED_COLOR = new Color(0x16A085);
	private static final Color HOVER_COLOR = PRESSED_COLOR;

	private boolean rollover = false;
	private boolean pressed = false;
	//private boolean clicked = false;
	private boolean focused = false;
	
	private ButtonSet set = null;
	
	protected void onPress() {
	}
	
	protected void onUnpress() {
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Color c = UNCLICKED_COLOR;
		if (rollover || focused /* || pressed */) {
			c = HOVER_COLOR;
		}
		if (pressed) {
			c = PRESSED_COLOR;
		}
		
		//System.out.println("___");
		//System.out.println(pressed);
		//System.out.println(clicked);
		//System.out.println(rollover);
		//System.out.println(focused);

		super.setBackground(c);
		super.paintComponent(g);
	}
	
	public void unpress() {
		if (set != null) {
			set.unregister(this);
		}
		pressed = false;
		onUnpress();
		repaint();
	}
	
	public void press() {
		if (set != null) {
			set.register(this);
		}
		pressed = true;
		onPress();
		repaint();
	}
	
	public ButtonSet getSet() {
		return set;
	}
	
	public boolean pressed() {
		return pressed;
	}
	
	private void init(String text, String iconfile, ActionListener listener, int w, int h) {
		addMouseListener(new MListener(this));
		addActionListener(new AListener(this));

		if (listener != null)
			addActionListener(listener);

		Dimension d = new Dimension(w, h);

		setMaximumSize(d);
		setMinimumSize(d);
		setPreferredSize(d);
		setSize(d);
		
		setBorder(null);
		setBackground(UNCLICKED_COLOR);
		
		Utils.addPadding(this, 10, 10, 10, 10);
		Utils.removeFocusBorder(this);
		Utils.addIcon(this, iconfile, 40, 40);
		Utils.enableEnterKey(this);
		
		setHorizontalAlignment(SwingConstants.LEFT);
		setRolloverEnabled(true);
		setForeground(FONT_COLOR);
		setFont(FONT);
		rollover = false;
		this.addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				focused = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				focused = false;
			}
		});
	}
	
	public FButton(String text, String iconfile) {
		super(text);
		init(text, iconfile, null, 100, 100);
	}

	public FButton(String text, String iconfile, ActionListener listener) {
		super(text);
		init(text, iconfile, listener, 100, 100);
	}

	public FButton(String text, String iconfile, int w, int h) {
		super(text);
		init(text, iconfile, null, w, h);
	}
	
	public FButton(String text, String iconfile, ActionListener listener, int w, int h) {
		super(text);
		init(text, iconfile, listener, w, h);
	}
	
	public void attachSet(ButtonSet set) {
		this.set = set;
	}
}
