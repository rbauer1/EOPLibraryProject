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

	private static final Font FONT = new Font("Arial", Font.TYPE1_FONT, 14);
	private static final Color FONT_COLOR = new Color(0xF0F0E8);

	private static final Color UNCLICKED_COLOR = new Color(0x1ABC9C);
	private static final Color PRESSED_COLOR = new Color(0x16A085);
	private static final Color HOVER_COLOR = PRESSED_COLOR;
	
	private static final int DEFAULT_WIDTH = 100;
	private static final int DEFAULT_HEIGHT = 100;

	private boolean mousePressed = false;
	private boolean rollover = false;
	private boolean pressed = false;
	private boolean focused = false;
	
	private ButtonSet set = null;

	private IconConfig iconConf;
	private ColorConfig colorConf = Colors.BASIC_COLOR;

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
	    	ref.mousePressed = true;
	    }

	    public void mouseReleased(MouseEvent e) {
	    	ref.mousePressed = false;
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
	
	public void setIconConfig(IconConfig conf) {
		iconConf = conf;
		update();
	}
	
	public void setColorConfig(ColorConfig conf) {
		colorConf = conf;
		update();
	}
	
	public void toggle() {
		if (pressed) {
			unpress();
		} else {
			press();
		}
	}
	
	protected void onPress() {
	}
	
	protected void onUnpress() {
	}
	
	private void update() {
		Color c = null;
		if (colorConf != null) {
			c = colorConf.getColor();
			
			if (mousePressed) {
				c = colorConf.getProgressColor();
			} else if (rollover || focused) {
				c = colorConf.getHoverColor();
			}
			if (pressed) {
				c = colorConf.getPressedColor();
			}
		}
		if (iconConf != null) {
			if (pressed) {
				setIcon(iconConf.getPressedIcon());
			} else {
				setIcon(iconConf.getIcon());
			}
		}
		
		if (c != null)
			setBackground(c);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		update();
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
	
	private void init(String text, IconConfig icons, ActionListener listener, int w, int h) {
		addMouseListener(new MListener(this));
		addActionListener(new AListener(this));

		if (listener != null)
			addActionListener(listener);

		Utils.setAllSize(this, w, h);
		setBorder(null);
		//setBackground(UNCLICKED_COLOR);
		update();
		
		Utils.removeFocusBorder(this);
		Utils.enableEnterKey(this);

		if (icons != null)
			setIconConfig(icons);
		
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
	
	public FButton(String text) {
		super(text);
		init(text, null, null, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, ActionListener listener) {
		super(text);
		init(text, null, listener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, ActionListener listener, int w, int h) {
		super(text);
		init(text, null, listener, w, h);
	}

	public FButton(String text, IconConfig icons) {
		super(text);
		init(text, icons, null, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, IconConfig icons, ActionListener listener) {
		super(text);
		init(text, icons, listener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, IconConfig icons, int w, int h) {
		super(text);
		init(text, icons, null, w, h);
	}
	
	public FButton(String text, IconConfig icons, ActionListener listener, int w, int h) {
		super(text);
		init(text, icons, listener, w, h);
	}
	
	public void attachSet(ButtonSet set) {
		this.set = set;
	}
}
