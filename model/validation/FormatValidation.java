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
	private Pattern format;
	
	/** Regular Expression matcher */
	private  Matcher matcher;

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
	 * Set Error message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		this.matcher = this.format.matcher((String)value);
		if(!this.matcher.matches()){
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}
		return true;
	}

}
