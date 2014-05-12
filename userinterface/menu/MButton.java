package userinterface.menu;

import java.awt.event.ActionListener;

import userinterface.MainFrame;
import userinterface.component.flat.FButton;
import userinterface.component.flat.IconConfig;
import userinterface.utilities.Utils;
import utilities.Key;

public class MButton extends FButton {
	private MenuArrow arrow = null;
	private static MButton currentUnpressed = null;
	private static MButton currentPressed = null;

	private static final int WIDTH = 80;
	private static final int HEIGHT = 80;

	public MButton(String title, IconConfig icons) {
		this(title, icons, null);
	}

	public MButton(String title, IconConfig icons, ActionListener listener) {
		super(title, icons, listener, WIDTH, HEIGHT);
		Utils.addPadding(this, 10, 10, 10, 10);
	}

	@Override
	public boolean onPrePress() {
		if(currentPressed == this){
			return false;
		}
		currentPressed = this;
		return true;
	}

	@Override
	public void onPress() {
		arrow = new MenuArrow(this);
	}

	@Override
	public boolean onPreUnpress() {
		currentUnpressed = this;
		return true;
	}

	@Override
	public void onReset() {
		if (arrow != null) {
			arrow.remove();
		}

		currentUnpressed = currentPressed = null;
	}

	@Override
	public void onUnpress() {
		if (arrow != null) {
			arrow.remove();
		}

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
