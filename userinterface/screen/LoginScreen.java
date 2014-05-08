package userinterface.screen;

import java.awt.Dimension;

import userinterface.view.View;

public class LoginScreen extends Screen {

	private static final long serialVersionUID = 5906551174459892121L;

	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;

	public LoginScreen(View view) {
		super(view);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	@Override
	public void addView(View view) {
		add(view);
	}

	@Override
	public void clearView() {
	}

	@Override
	public void processAction(Object source) {
	}

}
