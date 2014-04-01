package model.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Model Validation the ensures the field is not in the provide collection.
 */
public class ExclusionValidation extends Validation {
	
	/** Set of elements that the field cannot be */
	private Set<String> exclusionElements;
	
	/** Error message */
	private String message;

	/**
	 * Constructs a validation that ensures the field is not in the provided list.
	 * @param fieldKey
	 * @param fieldName
	 * @param exclusionElements
	 * @param message
	 */
	public ExclusionValidation(String fieldKey, String fieldName, Collection<String> exclusionElements, String message) {
		super(fieldKey, fieldName);
		this.message = message;
		this.exclusionElements = new HashSet<String>(exclusionElements);
	}
	
	/**
	 * Constructs a validation that ensures the field is not in the provided list.
	 * @param fieldKey
	 * @param fieldName
	 * @param exclusionElements
	 * @param message
	 */
	public ExclusionValidation(String fieldKey, String fieldName, String[] exclusionElements, String message) {
		super(fieldKey, fieldName);
		this.message = message;
		this.exclusionElements = new HashSet<String>();
		for(String element : exclusionElements){
			this.exclusionElements.add(element);
		}
	}
	
	/**
	 * Constructs a validation that ensures the field is not in the provided list.
	 * Default message is "fieldName cannot be %{value}". %{value} will be replaced by field value.
	 * @param fieldKey
	 * @param fieldName
	 * @param exclusionElements
	 */
	public ExclusionValidation(String fieldKey, String fieldName, Collection<String> exclusionElements) {
		this(fieldKey, fieldName, exclusionElements, "cannot be %{value}");
	}
	
	/**
	 * Constructs a validation that ensures the field is not in the provided list.
	 * Default message is "fieldName cannot be %{value}". %{value} will be replaced by field value.
	 * @param fieldKey
	 * @param fieldName
	 * @param exclusionElements
	 */
	public ExclusionValidation(String fieldKey, String fieldName, String[] exclusionElements) {
		this(fieldKey, fieldName, exclusionElements, "cannot be %{value}");
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
		String str = (String)value;
		if(this.exclusionElements.contains(str)){
			validator.addError(getFieldKey(), getFieldName() + " " + this.message.replace("%{value}", str));
			return false;
		}
		return true;
	}

}
