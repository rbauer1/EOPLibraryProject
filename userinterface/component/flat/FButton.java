package userinterface.component.flat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;

import userinterface.component.Button;
import userinterface.utilities.ButtonSet;
import userinterface.utilities.Utils;

public class FButton extends Button {

	private static final long serialVersionUID = -3487434463899202779L;
	private static final Font FONT = new Font("Arial", Font.TYPE1_FONT, 14);
	private static final Color FONT_COLOR = new Color(0xF0F0E8);

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
	
	public void reset() {
		if (set != null && pressed) {
			set.unregister(this);
		}
		setBackground(colorConf.getColor());
		rollover = false;
		pressed = false;
		focused = false;
		mousePressed = false;
		onReset();
		repaint();
	}
	
	public void setIconConfig(IconConfig conf) {
		iconConf = conf;
		update();
	}
	
	public void setColorConfig(ColorConfig conf) {
		super.setColorConfig(conf);
		update();
	}
	
	public void toggle() {
		if (pressed) {
			unpress();
		} else {
			press();
		}
	}
	
	protected void onReset() {
	}

	protected void onPrePress() {
	}
	
	protected void onPreUnpress() {
	}
	
	protected void onPress() {
	}
	
	protected void onUnpress() {
	}
	
	private void update() {
		if (iconConf != null) {
			if (pressed || mousePressed) {
				setIcon(iconConf.getPressedIcon());
			} else if(rollover) {
				setIcon(iconConf.getHoverIcon());
			} else if(focused){
				setIcon(iconConf.getHoverIcon());
			} else {
				setIcon(iconConf.getIcon());
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		update();
		super.paintComponent(g);
	}
	
	public void unpress() {
		onPreUnpress();
		if (set != null) {
			set.unregister(this);
		}
		pressed = false;
		onUnpress();
		repaint();
	}
	
	public void press() {
		onPrePress();
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

		if (listener != null){
			addActionListener(listener);
		}
		Utils.setAllSize(this, w, h);
		setBorder(null);
		//setBackground(UNCLICKED_COLOR);
		update();
		
		Utils.removeFocusBorder(this);
		Utils.enableEnterKey(this);

		if (icons != null){
			setIconConfig(icons);
		}
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
	
	public FButton(String text, boolean clear) {
		super(text, clear);
		init(text, null, null, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, ActionListener listener) {
		super(text);
		init(text, null, listener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public FButton(String text, ActionListener listener, boolean clear) {
		super(text, clear);
		init(text, null, listener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, ActionListener listener, int w, int h) {
		super(text);
		init(text, null, listener, w, h);
	}
	
	public FButton(String text, ActionListener listener, int w, int h, boolean clear) {
		super(text, clear);
		init(text, null, listener, w, h);
	}

	public FButton(String text, IconConfig icons) {
		super(text);
		init(text, icons, null, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public FButton(String text, IconConfig icons, boolean clear) {
		super(text, clear);
		init(text, icons, null, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, IconConfig icons, ActionListener listener) {
		super(text);
		init(text, icons, listener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public FButton(String text, IconConfig icons, ActionListener listener, boolean clear) {
		super(text, clear);
		init(text, icons, listener, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	
	public FButton(String text, IconConfig icons, int w, int h) {
		super(text);
		init(text, icons, null, w, h);
	}
	
	public FButton(String text, IconConfig icons, int w, int h, boolean clear) {
		super(text, clear);
		init(text, icons, null, w, h);
	}
	
	public FButton(String text, IconConfig icons, ActionListener listener, int w, int h) {
		super(text);
		init(text, icons, listener, w, h);
	}
	
	public FButton(String text, IconConfig icons, ActionListener listener, int w, int h, boolean clear) {
		super(text, clear);
		init(text, icons, listener, w, h);
	}
	
	public void attachSet(ButtonSet set) {
		this.set = set;
	}
}
