package userinterface.view;

import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.DateField;
import userinterface.component.flat.FButton;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

public class SettingsView extends View {
	
	private static final long serialVersionUID = 1163735168108817859L;
	
	private DateField dateField;
	private Button submitButton;
	
	public SettingsView(Controller controller) {
		super(controller, "Settings");
		subscribeToController(Key.MESSAGE);
	}
	
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
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
		controller.stateChangeRequest(Key.GET_CURRENT_MAX_DUE_DATE, null);
	}

	@Override
	public void processAction(Object source) {
		messagePanel.clear();
		if (source == submitButton) {
			controller.stateChangeRequest(Key.SET_DUE_DATE, dateField.getValue());
		}
	}

}
