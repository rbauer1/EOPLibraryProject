package model.validation;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Model Validation the ensures the field is in the provide collection.
 */
public class InclusionValidation extends Validation {
	
	/** Set of elements that the field must be in */
	private Set<String> inclusionElements;
	
	/** Error message */
	private String message;

	/**
	 * Constructs a validation that ensures the field is in the provided list.
	 * @param fieldKey
	 * @param fieldName
	 * @param inclusionElements
	 * @param message
	 */
	public InclusionValidation(String fieldKey, String fieldName, Collection<String> inclusionElements, String message) {
		super(fieldKey, fieldName);
		this.message = message;
		this.inclusionElements = new HashSet<String>(inclusionElements);
	}
	
	/**
	 * Constructs a validation that ensures the field is in the provided list.
	 * @param fieldKey
	 * @param fieldName
	 * @param inclusionElements
	 * @param message
	 */
	public InclusionValidation(String fieldKey, String fieldName, String[] inclusionElements, String message) {
		super(fieldKey, fieldName);
		this.message = message;
		this.inclusionElements = new HashSet<String>();
		for(String element : inclusionElements){
			this.inclusionElements.add(element);
		}
	}
	
	/**
	 * Constructs a validation that ensures the field is in the provided list.
	 * Default message is "fieldName is not included in the list".
	 * @param fieldKey
	 * @param fieldName
	 * @param inclusionElements
	 */
	public InclusionValidation(String fieldKey, String fieldName, Collection<String> inclusionElements) {
		this(fieldKey, fieldName, inclusionElements, "is not included in the list");
	}
	
	/**
	 * Constructs a validation that ensures the field is in the provided list.
	 * Default message is "fieldName is not included in the list".
	 * @param fieldKey
	 * @param fieldName
	 * @param inclusionElements
	 */
	public InclusionValidation(String fieldKey, String fieldName, String[] inclusionElements) {
		this(fieldKey, fieldName, inclusionElements, "is not included in the list");
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
		String str = (String)value;
		if(!this.inclusionElements.contains(str)){
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}
		return true;
	}

}
