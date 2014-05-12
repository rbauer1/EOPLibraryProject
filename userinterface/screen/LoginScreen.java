package userinterface.screen;

import userinterface.view.View;

public class LoginScreen extends Screen {

	private static final long serialVersionUID = 5906551174459892121L;

	public static final int WIDTH = 350;
	public static final int HEIGHT = 215;

	public LoginScreen(View view) {
		super(view);
	}

	@Override
	public void addView(View view) {
		add(view);
	}

	@Override
	public void clearView() {
		removeAll();
	}

	@Override
	public void processAction(Object source) {
	}

}
