package model.validation;

public class EqualityValidation extends Validation {
	
	/** Field key that fieldKey must equal */
	private String otherFieldKey;
	
	/** display name for other fieldFieldKey */
	private String otherFieldName;

	public EqualityValidation(String fieldKey, String fieldName, String otherFieldKey, String otherFieldName) {
		super(fieldKey, fieldName);
		this.otherFieldKey = otherFieldKey;
		this.otherFieldName = otherFieldName;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		String fieldValue = value == null ? "" : (String)value;
		
		Object otherValue = validator.getModel().getState(otherFieldKey);
		String otherFieldValue = otherValue == null ? "" : (String)otherValue;
		
		if(!fieldValue.equals(otherFieldValue)){
			validator.addError(getFieldKey(), getFieldName() + " does not match " + otherFieldName);
			return false;
		}
		return true;
	}

}
