package model.validation;

/**
 * Model Validation that ensures the field is alphanumeric
 */
public class AlphaNumericValidation extends FormatValidation {
	
	private static final String ALPHA_NUMERIC_REGEX = "^[a-zA-Z0-9 ]*$";

	/**
	 * Constructs a validation that ensures the field is alphanumeric.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public AlphaNumericValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName, ALPHA_NUMERIC_REGEX, message);
	}

	/**
	 * Constructs a validation that ensures the field is alphanumeric.
	 * Default message is "fieldName must be alphanumeric"
	 * @param fieldKey
	 * @param fieldName
	 */
	public AlphaNumericValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "must be alphanumeric");
	}

}
