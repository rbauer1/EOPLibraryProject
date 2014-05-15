package controller.transaction;

import java.util.List;
import java.util.Properties;

import model.MaxDueDate;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;
import exception.InvalidPrimaryKeyException;

public class ChangeMaxDueDateTransaction extends Transaction{

	public ChangeMaxDueDateTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute() {
		showView("SettingsView");
	}
	
	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SET_DUE_DATE)){
			setMaxDueDate((String) value);
		} else if(key.equals(Key.GET_CURRENT_MAX_DUE_DATE)){
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.INFO, "Hello! The current max due date is set to " + getCurrentMaxDueDate() + " To change it, set the dropdowns below to the desired date and hit submit!"));
		}
		super.stateChangeRequest(key, value);
	}
	
	private String getCurrentMaxDueDate(){
		try {
			MaxDueDate maxDueDate = new MaxDueDate();
			return maxDueDate.getPrimaryKeyValue();
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while getting the current max due date."));
			e.printStackTrace();
			return null;
		}
	}
	
	private void setMaxDueDate(String date){
		MaxDueDate maxDueDate;
		try {
			maxDueDate = new MaxDueDate();
			maxDueDate.stateChangeRequest(Key.SET_DUE_DATE, date);
			if(maxDueDate.save()){
				stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.SUCCESS, "Well done! The max due date was sucessfully set!"));
			}else{
				List<String> inputErrors = maxDueDate.getErrors();
				if(inputErrors.size() > 0){
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Aw shucks! There are errors in the input. Please try again.", inputErrors));
				}else{
					stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
				}
			}
		} catch (InvalidPrimaryKeyException e) {
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while saving."));
			e.printStackTrace();
		}
	}

}
