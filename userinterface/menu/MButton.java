package userinterface.menu;

import java.awt.event.ActionListener;

import userinterface.component.flat.FButton;
import userinterface.utilities.ButtonSet;

public class MButton extends FButton {
	private static final ButtonSet globalSet = new ButtonSet();
	private MenuArrow arrow = null;
	
	private static final int WIDTH = 80;
	private static final int HEIGHT = 80;

	// FIXME: could be more intuitive
	private static final int ARROW_SIZE = 40;

	public MButton(String title, String iconfile, ActionListener listener) {
		super(title, iconfile, listener, WIDTH, HEIGHT);
		attachSet(globalSet);
	}
	
	public MButton(String title, String iconfile) {
		this(title, iconfile, null);
	}
	
	protected void onPress() {
		arrow = new MenuArrow(this, ARROW_SIZE, ARROW_SIZE);
	}
	
	protected void onUnpress() {
		if (arrow != null)
			arrow.remove();
	}
}
