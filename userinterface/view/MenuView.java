package userinterface.view;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.Button;
import userinterface.component.flat.FButton;
import userinterface.component.flat.Icons;
import userinterface.utilities.Utils;
import utilities.Key;
import controller.Controller;

public abstract class MenuView extends View {
	
	private static final long serialVersionUID = -4462137345508528750L;

	/* Buttons */
	protected FButton addButton;
	protected FButton modifyButton;
	protected FButton deleteButton;
	
	private static final int BUTTON_HEIGHT = 100;
	private static final int BUTTON_WIDTH = 250;
	private static final int BUTTON_PADDING = 10;
	private static final int BUTTON_ICON_SIZE = 50;

	/**
	 * Constructs book menu view
	 * @param controller
	 */
	public MenuView(Controller controller, String title) {
		super(controller, title);
	}
	
	protected abstract String getMenuName();
	
	private void initButton(FButton button) {
		Utils.setAllSize(button, BUTTON_WIDTH, BUTTON_HEIGHT);
		Utils.addPadding(button, BUTTON_PADDING);
		button.setIconConfig(new Icons.IconConfigHelper(getMenuName() + "_Clear.png", BUTTON_ICON_SIZE, BUTTON_ICON_SIZE));
	}
	
	private JPanel createSeparator() {
		JPanel sep = new JPanel();
		
		Utils.setAllSize(sep, 2, HEIGHT);
		return sep;
	}

	@Override
	protected void build() {
		String suffix = getMenuName();

		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
			addButton = new FButton("Add " + suffix, this);
			initButton(addButton);

            modifyButton = new FButton("Modify " + suffix, this);
            initButton(modifyButton);

            deleteButton = new FButton("Delete " + suffix, this);
            initButton(deleteButton);
		}
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(addButton);
		panel.add(createSeparator());
		panel.add(modifyButton);
		panel.add(createSeparator());
		panel.add(deleteButton);
		
		add(panel);
	}

}
