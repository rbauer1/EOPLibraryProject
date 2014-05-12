package userinterface.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Accordion;
import userinterface.component.flat.Colors;
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
	protected Accordion body;

	private static final int BUTTON_HEIGHT = 50;
	private static final int BUTTON_WIDTH = 500;
	private static final int BUTTON_PADDING = 10;

	private static final int MAIN_BUTTON_HEIGHT = 80;
	private static final int MAIN_BUTTON_WIDTH = 250;
	private static final int MAIN_BUTTON_PADDING = 10;
	private static final int MAIN_BUTTON_ICON_SIZE = 50;

	/**
	 * Constructs book menu view
	 * @param controller
	 */
	public MenuView(Controller controller, String title) {
		super(controller, title);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
	}

	protected abstract String getMenuName();

	private void initButton(FButton button, String suffix) {
		Utils.setAllSize(button, MAIN_BUTTON_WIDTH, MAIN_BUTTON_HEIGHT);
		Utils.addPadding(button, MAIN_BUTTON_PADDING);
		button.setIconConfig(new Icons.IconConfigHelper("Programs/" + getMenuName() + "_" + suffix + ".png", MAIN_BUTTON_ICON_SIZE, MAIN_BUTTON_ICON_SIZE));
	}

	private JPanel createMainButtonSeparator() {
		JPanel sep = new JPanel();

		Utils.setAllSize(sep, 2, HEIGHT);
		return sep;
	}

	protected JPanel createButtonSeparator() {
		JPanel sep = new JPanel();
		Utils.setAllSize(sep, BUTTON_WIDTH, 5);

		return sep;
	}

	protected FButton createButton(String text) {
		FButton button = new FButton(text, this);
		Utils.setAllSize(button, BUTTON_WIDTH, BUTTON_HEIGHT);
		Utils.addPadding(button, BUTTON_PADDING);

		return button;
	}
	
	protected FButton createBackButton(String text) {
		FButton button = new FButton(text, this);
		Utils.setAllSize(button, BUTTON_WIDTH, BUTTON_HEIGHT);
		Utils.addPadding(button, BUTTON_PADDING);
		button.setColorConfig(Colors.RED);

		return button;
	}
	
	private JPanel createBlockSeparator() {
		JPanel sep = new JPanel();
		Utils.setTransparent(sep);
		Utils.setAllSize(sep, WIDTH, 40);
		
		return sep;
	}

	@Override
	public void beforeShown() {
		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
			addButton.reset();
			modifyButton.reset();
			deleteButton.reset();
		}
	}

	@Override
	public void afterShown() {
	}

	@Override
	protected void build() {
		String suffix = getMenuName();

		add(createBlockSeparator());
		if (((Worker)controller.getState(Key.WORKER)).isAdmin()){
			addButton = new FButton("Add " + suffix, this);
			initButton(addButton, "Add");

            modifyButton = new FButton("Modify " + suffix, this);
            initButton(modifyButton, "Modify");

            deleteButton = new FButton("Delete " + suffix, this);
            initButton(deleteButton, "Delete");
            JPanel panel = new JPanel();
            
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(addButton);
            panel.add(createMainButtonSeparator());
            panel.add(modifyButton);
            panel.add(createMainButtonSeparator());
            panel.add(deleteButton);
            add(panel);
		}

		add(createBlockSeparator());

		body = new Accordion();
		// The height of the accordion change dynamically
		Utils.setAllSize(body, BUTTON_WIDTH, 0);

		add(body);
	}

}
