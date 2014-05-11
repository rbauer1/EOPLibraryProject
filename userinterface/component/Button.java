package userinterface.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Button that is styled to match the application
 */
public class Button extends JButton{

	private static final long serialVersionUID = -8920690359263819759L;

	/** Font used for general items */
	private static final Font FONT = new Font("Arial", Font.TYPE1_FONT, 14);

	/** Font Color of button */
	private static final Color FONT_COLOR = new Color(0xF0F0E8);

	/** Size of Button **/
	private static final Dimension SIZE = new Dimension(195, 25);

	/** Lighter color for background gradient **/
	private static final Color LIGHT_COLOR = new Color(0x668D3C);

	/** Darker color for background gradient **/
	private static final Color DARK_COLOR = LIGHT_COLOR.darker();

	/** Button Border color **/
	public static final Color BORDER_COLOR = DARK_COLOR;

	/** True if mouse over button **/
	private boolean rollover;

	/** True while button is pressed **/
	private boolean pressed;

	/** True if the button has focus **/
	private boolean focused;

	/** True if the button is disabled **/
	private boolean disabled;
	
	/** True if button is not to be painted colors **/
	private boolean clear;
	
	public Button() {
		super();
		initialize();
	}

	/**
	 * @param text
	 */
	public Button(String text) {
		super(text);
		clear = false;
		initialize();
	}
	
	public Button(String text, boolean clearButton) {
		super(text);
		clear = clearButton;
		initialize();
	}

	public Button(String text, ActionListener listener) {
		this(text);
		clear = false;
		addActionListener(listener);
	}
	
	public Button(String text, ActionListener listener, boolean clearButton) {
		this(text);
		clear = clearButton;
		addActionListener(listener);
	}

	private void initialize(){
		setRolloverEnabled(true);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setForeground(FONT_COLOR);
		setFont(FONT);
		setMaximumSize(SIZE);
		setMinimumSize(SIZE);
		setPreferredSize(SIZE);
		setAlignmentX(CENTER_ALIGNMENT);
		rollover = false;
		addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent e) {
				focused = true;
			}
			@Override
			public void focusLost(FocusEvent e) {
				focused = false;
			}
		});
		getModel().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				ButtonModel model = (ButtonModel) e.getSource();
				disabled = ! model.isEnabled();
				rollover = model.isRollover();
				pressed = model.isPressed();
				repaint();
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		if(clear){
			super.paintComponent(g);
		}else{
			paintColor(g);
		}
	}
	
	private void paintColor(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int height = getHeight();
		int width  = getWidth();
		Color color = LIGHT_COLOR;
		if(disabled){
			color = Color.GRAY;
			setForeground(Color.GRAY.brighter());
		}else{
			setForeground(FONT_COLOR);
			if(rollover || focused){
				color = color.brighter();
			}
			if(pressed){
				color = DARK_COLOR;
			}
		}
		g2.setPaint(color);
		g2.fillRect(0, 0, width, height);
		//outer border
//		g2.setPaint(BORDER_COLOR);
//		g2.drawRoundRect(0, 0, width-1, height-1, 4, 4);

		//make corners match background
//		g2.setPaint(Panel.BACKGROUND_COLOR);
//		g2.drawLine(0, 0, 0, 0);
//		g2.drawLine(0, height-1, 0, height-1);
//		g2.drawLine(width-1, 0, width-1, 0);
//		g2.drawLine(width-1, height-1, width-1, height-1);

		super.paintComponent(g);
	}
}
