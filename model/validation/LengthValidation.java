package model.validation;

/**
 * Model Validation that ensures field length is correct
 */
public class LengthValidation extends Validation {
	/** Min length of field */
	private int minSize;
	
	/** Max length of field */
	private int maxSize;
	
	/** Error message displayed when field length less than minSize */
	private String tooShortMessage;
	
	/** Error message displayed when field length greater than maxSize */
	private String tooLongMessage;
	
	/** Error message displayed when field length not equal to equal value */
	private String notEqualToMessage;
	
	/** Allow field to be empty */
	private  boolean allowEmpty;

	/**
	 * Constructs validation for field key that ensures field length 
	 * is between minSize and maxSize (Inclusive). If minSize == maxSize,
	 * then validation ensures field length is equal to minSize also.
	 * @param fieldKey
	 * @param fieldName
	 * @param minSize
	 * @param maxSize
	 * @param allowEmpty
	 */
	public LengthValidation(String fieldKey, String fieldName, int minSize, int maxSize, boolean allowEmpty) {
		super(fieldKey, fieldName);
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.tooShortMessage = "is too short (Minimum length is %{length})";
		this.tooLongMessage = "is too long (Maximum length is %{length})";
		this.notEqualToMessage = "must have a length of %{length}";
		this.allowEmpty = allowEmpty;
	}
	
	/**
	 * Constructs validation for field key that ensures field length 
	 * is between minSize and maxSize (Inclusive). If minSize == maxSize,
	 * then validation ensures field length is equal to minSize also.
	 * @param fieldKey
	 * @param fieldName
	 * @param minSize
	 * @param maxSize
	 */
	public LengthValidation(String fieldKey, String fieldName, int minSize, int maxSize) {
		this(fieldKey, fieldName, minSize, maxSize, false);
	}
	
	/**
	 * Constructs validation for field key that ensures field length 
	 * is equal to equalToSize.
	 * @param fieldKey
	 * @param fieldName
	 * @param equalToSize
	 */
	public LengthValidation(String fieldKey, String fieldName, int equalToSize) {
		this(fieldKey, fieldName, equalToSize, false);
	}
	
	/**
	 * Constructs validation for field key that ensures field length 
	 * is equal to equalToSize.
	 * @param fieldKey
	 * @param fieldName
	 * @param equalToSize
	 * @param allowEmpty
	 */
	public LengthValidation(String fieldKey, String fieldName, int equalToSize, boolean allowEmpty) {
		this(fieldKey, fieldName, equalToSize, equalToSize, allowEmpty);
	}
	
	/**
	 * Set error message displayed when field length less than minSize.
	 * The pattern "%{length}" is replaced by the minSize.
	 * @param tooShortMessage
	 */
	public void setTooShortMessage(String tooShortMessage) {
		this.tooShortMessage = tooShortMessage;
	}

	/**
	 * Set error message displayed when field length greater than maxSize
	 * The pattern "%{length}" is replaced by the maxSize.
	 * @param tooLongMessage
	 */
	public void setTooLongMessage(String tooLongMessage) {
		this.tooLongMessage = tooLongMessage;
	}

	/**
	 * Set error message displayed when field length not equal to equal value
	 * The pattern "%{length}" is replaced by the equalToSize.
	 * @param notEqualToMessage
	 */
	public void setNotEqualToMessage(String notEqualToMessage) {
		this.notEqualToMessage = notEqualToMessage;
	}

	@Override
	public boolean execute(Object value, ModelValidator validator) {
		if(value == null){
			value = "";
		}
		String str = (String)value;
		if(allowEmpty && str.length() == 0){
			return true;
		}
		
		if(this.minSize == this.maxSize && str.length() != this.minSize){
			validator.addError(getFieldKey(), getFieldName() + " " + 
					this.notEqualToMessage.replace("%{length}", this.minSize + ""));
			return false;
		}
		if(str.length() < this.minSize){
			validator.addError(getFieldKey(), getFieldName() + " " + 
					this.tooShortMessage.replace("%{length}", this.minSize + ""));
			return false;
		}
		if(str.length() > this.maxSize){
			validator.addError(getFieldKey(), getFieldName() + " " + 
					this.tooLongMessage.replace("%{length}", this.maxSize + ""));
			return false;
		}
		return true;
	}

}
