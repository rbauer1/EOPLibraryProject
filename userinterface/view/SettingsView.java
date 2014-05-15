package userinterface.view;

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.DateField;
import userinterface.message.MessageType;
import controller.Controller;

public class SettingsView extends View {
	
	private static final long serialVersionUID = 1163735168108817859L;
	
	private DateField dateField;
	private Button submitButton;

	public SettingsView(Controller controller) {
		super(controller, "Settings");
	}
	
	@Override
	public void updateState(String key, Object value) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void build() {
		dateField = new DateField();
		add(ViewHelper.formatFieldCenter("Semester Due Date", dateField));

		submitButton = new Button("Submit", this);
		add(ViewHelper.formatCenter(submitButton));
	}

	@Override
	public void afterShown() {
		messagePanel.clear();
	}

	@Override
	public void processAction(Object source) {
		if (source == submitButton) {
			messagePanel.displayMessage(MessageType.SUCCESS, "The new max due date is: " + dateField.getValue() + "!");
		}
	}

}
