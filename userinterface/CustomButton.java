package userinterface;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomButton extends JButton{
	private static final long serialVersionUID = -8920690359263819759L;
	private boolean rollover;
	private boolean pressed;
	public CustomButton() {
		super();
		initializeButton();
	}

	public CustomButton(String text) {
		super(text);
		initializeButton();
	}
	
	private void initializeButton(){
		setRolloverEnabled(true);
		setContentAreaFilled(false);
		setForeground(View.BACKGROUND_COLOR);
		rollover = false;
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
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = getHeight();
		int width  = getWidth();
		Color gradientLight = View.SEPARATOR_COLOR;
		Color gradientDark = View.SEPARATOR_COLOR.darker();
		if(rollover){ 
			gradientLight = gradientLight.brighter();
			gradientDark = gradientDark.brighter();
		}
		if(pressed){
			Color temp = gradientLight;
			gradientLight = gradientDark;
			gradientDark = temp;
		}
		g2.setPaint(new GradientPaint(0, 0, gradientLight, 0,
				height, gradientDark));
		g2.fillRect(0, 0, width, height);
		//outer border
		g2.setPaint(View.TITLE_COLOR);
		g2.drawRoundRect(0, 0, width-1, height-1, 4, 4);
		
		
		//make corners match background
		g2.setPaint(View.BACKGROUND_COLOR);
		g2.drawLine(0, 0, 0, 0);
		g2.drawLine(0, height-1, 0, height-1);
		g2.drawLine(width-1, 0, width-1, 0);
		g2.drawLine(width-1, height-1, width-1, height-1);
	
		super.paintComponent(g);
	}
}
