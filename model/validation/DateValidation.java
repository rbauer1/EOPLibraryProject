package model.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Model Validation that ensures field is a valid date.
 * Tries numerous formats and then updates the model with the default format.
 */
public class DateValidation extends Validation {
	
	private static final Pattern[] DATE_PATTERNS = {
		Pattern.compile("(\\d){4}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])"), // yyyy-MM-dd
		Pattern.compile("(\\d){4}/(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])"), // yyyy/MM/dd
		Pattern.compile("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-(\\d){4}"), // MM-dd-yyyy
		Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d){4}"), // MM/dd/yyyy
		Pattern.compile("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-(\\d){2}"), // MM-dd-yy
		Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/(\\d){2}")  // MM/dd/yy
	};
	
	private static final SimpleDateFormat[] DATE_FORMATS = {
		new SimpleDateFormat("yyyy-MM-dd"),
		new SimpleDateFormat("yyyy/MM/dd"),
		new SimpleDateFormat("MM-dd-yyyy"),
		new SimpleDateFormat("MM/dd/yyyy"),
		new SimpleDateFormat("MM-dd-yy"),
		new SimpleDateFormat("MM/dd/yy")
	};
	
	/** Error message displayed when invalid format */
	private String message;

	/**
	 * Constructs date validation for fieldKey.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public DateValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName);
		this.message = message;
	}
	
	/**
	 * Constructs date validation for fieldKey.
	 * Default message is "fieldName must be a valid date (yyyy-mm-dd)"
	 * @param fieldKey
	 * @param fieldName
	 */
	public DateValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "must be a valid date (yyyy-mm-dd)");
	}

	/**
	 * Sets error message
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean execute(Object val, ModelValidator validator) {
		String value = ((String)val).replace(" ", "");
		Date date = null;
		try {
			for(int i = 0; i < DATE_PATTERNS.length && date == null; i++){
				if(DATE_PATTERNS[i].matcher(value).matches()){
					date = DATE_FORMATS[i].parse(value);
				}
			}
		} catch (ParseException e) {
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}
		if(date == null){
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}
		validator.getModel().stateChangeRequest(getFieldKey(), DATE_FORMATS[0].format(date));
		return true;
	}

}
