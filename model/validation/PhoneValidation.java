package model.validation;

/**
 * Model Validation that ensures the field is a phone number.
 * Updates field in model to be in the default format: 555-555-5555
 */
public class PhoneValidation extends FormatValidation {
	
	private static final String PHONE_REGEX = "(\\d{10})|" + 
			"(\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4})|" + 
			"(\\(\\d{3}\\)[-\\.\\s]?\\d{3}[-\\.\\s]\\d{4})";

	/**
	 * Constructs a validation that ensures the field is a phone number.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public PhoneValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName, PHONE_REGEX, message);
	}

	/**
	 * Constructs a validation that ensures the field is a phone number.
	 * Default message is "fieldName must be valid phone number".
	 * @param fieldKey
	 * @param fieldName
	 */
	public PhoneValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "must be valid phone number");
	}
	
	@Override
	public boolean execute(Object value, ModelValidator validator) {
		String str = (String)value;
		if(super.execute(value, validator)){
			str = str.replaceAll("[^\\d]", "");
			str = str.substring(0, 3) + "-" + str.substring(3, 6) + "-" + str.substring(6, 10);
			validator.getModel().stateChangeRequest(getFieldKey(), str);
			return true;
		}
		return false;		
	}

}
