package userinterface.screen;

import java.awt.BorderLayout;

import userinterface.HeaderPanel;
import userinterface.view.View;

public class LoginScreen extends Screen {

	private static final long serialVersionUID = 5906551174459892121L;

	public static final int WIDTH = 350;
	public static final int HEIGHT = 215;

	public LoginScreen(View view) {
		super(view);
		setLayout(new BorderLayout());

		add(new HeaderPanel(), BorderLayout.NORTH);
	}

	@Override
	public void addView(View view) {
		add(view, BorderLayout.CENTER);
	}

	@Override
	public void updateState(String key, Object value) {
	}
}
