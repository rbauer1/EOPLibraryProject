package userinterface.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
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
	
	public Button() {
		super();
		initialize();
	}

	/**
	 * @param text
	 */
	public Button(String text) {
		super(text);
		initialize();
	}
	
	private void initialize(){
		setRolloverEnabled(true);
		setContentAreaFilled(false);
		setForeground(FONT_COLOR);
		setFont(FONT);
		setPreferredSize(SIZE);
		setAlignmentX(CENTER_ALIGNMENT);
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
		this.getModel().addChangeListener(new ChangeListener() {
		    @Override
		    public void stateChanged(ChangeEvent e) {
		        ButtonModel model = (ButtonModel) e.getSource();
		        rollover = model.isRollover();
		        pressed = model.isPressed();
		        repaint();
		    }
		});
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int height = getHeight();
		int width  = getWidth();
		Color gradientLight = LIGHT_COLOR;
		Color gradientDark = DARK_COLOR;
		if(rollover || focused){ 
			gradientLight = gradientLight.brighter();
			gradientDark = gradientDark.brighter();
		}
		if(pressed){
			Color temp = gradientLight;
			gradientLight = gradientDark;
			gradientDark = temp;
		}
		g2.setPaint(new GradientPaint(0, 0, gradientLight, 0,height, gradientDark));
		g2.fillRect(0, 0, width, height);
		
		//outer border
		g2.setPaint(BORDER_COLOR);
		g2.drawRoundRect(0, 0, width-1, height-1, 4, 4);
				
		//make corners match background
		g2.setPaint(Panel.BACKGROUND_COLOR);
		g2.drawLine(0, 0, 0, 0);
		g2.drawLine(0, height-1, 0, height-1);
		g2.drawLine(width-1, 0, width-1, 0);
		g2.drawLine(width-1, height-1, width-1, height-1);
	
		super.paintComponent(g);
	}
}
