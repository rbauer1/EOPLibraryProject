package userinterface.screen;

import impresario.IView;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import userinterface.view.View;
import controller.Controller;

public class LoginScreen extends Screen {
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	
	public LoginScreen(View view) {
		super(view);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void addView(View view) {
		add((JPanel)view);
	}
	
	public void processAction(Object source) {
	}

	public void clearView() { 
	}

}
