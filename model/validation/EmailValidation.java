package model.validation;

/**
 * Model Validation that ensures field is valid email.
 */
public class EmailValidation extends FormatValidation {
	
	private static final String EMAIL_REGEX = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/**
	 * Constructs a validation that ensures the field is an email.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public EmailValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName, EMAIL_REGEX, message);
	}

	/**
	 * Constructs a validation that ensures the field is a banner id.
	 * Default message is "fieldName must be valid email"
	 * @param fieldKey
	 * @param fieldName
	 */
	public EmailValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "must be valid email");
	}

}
