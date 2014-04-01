package model.validation;

/**
 * Model Validation that ensures field is not empty (null or length of 0).
 */
public class PresenceValidation extends Validation {
	
	/** Error message */
	private String message;

	/**
	 * Constructs a validation that ensures the field is not empty.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public PresenceValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName);
		this.message = message;
	}
	
	/**
	 * Constructs a validation that ensures the field is not empty.
	 * Default Message is "fieldName is required".
	 * @param fieldKey
	 * @param fieldName
	 */
	public PresenceValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "is required");
	}

	/**
	 * Set error message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		if(value == null){
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}
		String str = (String)value;
		if(str.length() == 0){
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}
		return true;
	}

}