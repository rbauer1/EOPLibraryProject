package model.validation;

/**
 * Base Class for a model validation. Used as the base of the command pattern.
 * Every model validation must extend this class.
 */
public abstract class Validation {
	
	/** Key for the model attribute */
	private String fieldKey;
		
	/** Display name for the model attribute */
	private String fieldName;

	/**
	 * Constructs a model validation.
	 * @param fieldKey
	 * @param fieldName
	 */
	protected Validation(String fieldKey, String fieldName){
		this.fieldKey = fieldKey;
		this.fieldName = fieldName;
	}
	
	/**
	 * Performs validation on the provided value.
	 * @param value
	 * @param validator
	 * @return true if validation passes
	 */
	public abstract boolean execute(Object value, ModelValidator validator);

	/**
	 * Returns the key to access the value in the model.
	 * @return field key
	 */
	public String getFieldKey() {
		return fieldKey;
	}
	
	/**
	 * Returns the display name for the field.
	 * @return field name
	 */
	public String getFieldName() {
		return fieldName;
	}
}
