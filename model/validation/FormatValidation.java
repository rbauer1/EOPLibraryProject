package model.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Model Validation that ensures field follows the provided validation.
 */
public class FormatValidation extends Validation {

	/** Error message */
	private String message;

	/** Regular Expression */
	private final Pattern format;

	/** Regular Expression matcher */
	private  Matcher matcher;

	/** Allow field to be empty */
	private  boolean allowEmpty;

	/**
	 * Constructs a validation that ensures the field is of the provided format.
	 * Default message is "fieldName is invalid".
	 * @param fieldKey
	 * @param fieldName
	 * @param format
	 */
	public FormatValidation(String entityStateKey, String fieldName, String format) {
		this(entityStateKey, fieldName, format, "is invalid");
	}

	/**
	 * Constructs a validation that ensures the field is of the provided format.
	 * @param fieldKey
	 * @param fieldName
	 * @param format
	 * @param message
	 */
	public FormatValidation(String fieldKey, String fieldName, String format, String message) {
		super(fieldKey, fieldName);
		this.format = Pattern.compile(format);
		this.message = message;
	}

	/**
	 * Allow field to be empty
	 */
	public void allowEmpty() {
		allowEmpty = true;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		if (value == null || value.toString().length() == 0) {
			return allowEmpty;
		}
		matcher = format.matcher((String)value);
		if(!matcher.matches()){
			validator.addError(getFieldKey(), getFieldName() + " " + message);
			return false;
		}
		return true;
	}

	/**
	 * Set Error message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
