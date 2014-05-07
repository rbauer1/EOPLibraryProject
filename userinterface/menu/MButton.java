package userinterface.menu;

import java.awt.event.ActionListener;

import userinterface.MainFrame;
import userinterface.component.flat.FButton;
import userinterface.component.flat.IconConfig;
import userinterface.view.panel.MenuPanel;
import userinterface.utilities.ButtonSet;
import userinterface.utilities.Utils;
import utilities.Key;

public class MButton extends FButton {
	private MenuArrow arrow = null;
	private static MButton currentUnpressed = null;
	private static MButton currentPressed = null;
	
	private static final int WIDTH = 80;
	private static final int HEIGHT = 80;

	// FIXME: could be more intuitive
	private static final int ARROW_SIZE = 40;

	public MButton(String title, IconConfig icons, ActionListener listener) {
		super(title, icons, listener, WIDTH, HEIGHT);
		Utils.addPadding(this, 10, 10, 10, 10);
	}
	
	public MButton(String title, IconConfig icons) {
		this(title, icons, null);
	}
	
	protected void onPrePress() {
		currentPressed = this;
	}
	
	protected void onPreUnpress() {
		currentUnpressed = this;
	}
	
	protected void onPress() {
		arrow = new MenuArrow(this, ARROW_SIZE, ARROW_SIZE);
	}
	
	protected void onUnpress() {
		if (arrow != null)
			arrow.remove();
		
		if (currentUnpressed == currentPressed) {
			MainFrame.getInstance()
			  .getScreen()
			  .getView()
			  .getController()
			  .stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);

			currentUnpressed = currentPressed = null;
		}
	}
}
