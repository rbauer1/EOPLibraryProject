package model.validation;

/**
 * Model Validation that ensures field is numeric and follows specified criteria.
 */
public class NumericValidation extends Validation {
	
	/** Ensure field be an integer */
	private boolean requireInteger;
	
	/** Error message if not numeric */
	private String message;
	
	/** Error message if not within specified range */
	private String rangeMessage = "must be %{type} %{value}";
	
	/** Ensure field greater than greaterThanValue */
	private Double greaterThanValue;
	
	/** Ensure field greater than or equal to greaterThanOrEqualToValue */
	private Double greaterThanOrEqualToValue;
	
	/** Ensure field equal to equalToValue */
	private Double equalToValue;
	
	/** Ensure field less than or equak to lessThanOrEqualToValue */
	private Double lessThanOrEqualToValue;
	
	/** Ensure field less than lessThanValue */
	private Double lessThanValue;
	
	/** Ensure field be even */
	private Boolean requireEven;
	
	/** Ensure field be odd */
	private Boolean requireOdd;
	
	/** Error Message for invalid parity */
	private String parityMessage = "must be %{parity}";

	/**
	 * Constructs validation that ensures that field is numeric.
	 * Ensures integer if requireInteger is true.
	 * @param fieldKey
	 * @param fieldName
	 * @param requireInteger
	 * @param message
	 */
	public NumericValidation(String fieldKey, String fieldName, boolean requireInteger, String message) {
		super(fieldKey, fieldName);
		this.requireInteger = requireInteger;
		this.message = message;
	}
	
	/**
	 * Constructs validation that ensures that field is numeric.
	 * Default message is "fieldName must be a number".
	 * If requireInteger is true, ensures integer and default message 
	 * is  "fieldName must be an integer".
	 * @param fieldKey
	 * @param fieldName
	 * @param requireInteger
	 */
	public NumericValidation(String fieldKey, String fieldName, boolean requireInteger) {
		this(fieldKey, fieldName, requireInteger, requireInteger ? "must be an integer" : "must be a number");
	}
	
	/**
	 * Constructs validation that ensures that field is numeric.
	 * Default message is "fieldName must be a number".
	 * @param fieldKey
	 * @param fieldName
	 */
	public NumericValidation(String entityStateKey, String fieldName) {
		this(entityStateKey, fieldName, false);
	}

	/**
	 * Set numeric error message.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Set range error message.
	 * @param message
	 */
	public void setRangeMessage(String message) {
		this.rangeMessage = message;
	}
	
	/**
	 * Set parity(odd/even) error message.
	 * @param message
	 */
	public void setParityMessage(String message) {
		this.parityMessage = message;
	}

	/**
	 * Require field be greater than provided value.
	 * @param greaterThanValue
	 */
	public void requireGreaterThan(double greaterThanValue) {
		this.greaterThanValue = greaterThanValue;
	}

	/**
	 * Require field be greater than or equal to provided value.
	 * @param greaterThanOrEqualToValue
	 */
	public void requireGreaterThanOrEqualTo(double greaterThanOrEqualToValue) {
		this.greaterThanOrEqualToValue = greaterThanOrEqualToValue;
	}

	/**
	 * Require field be equal to provided value.
	 * @param equalToValue
	 */
	public void require(double equalToValue) {
		this.equalToValue = equalToValue;
	}

	/**
	 * Require field be less than or equal to provided value.
	 * @param lessThanOrEqualToValue
	 */
	public void requireLessThanOrEqualTo(double lessThanOrEqualToValue) {
		this.lessThanOrEqualToValue = lessThanOrEqualToValue;
	}

	/**
	 * Require field be less than provided value.
	 * @param lessThanValue
	 */
	public void requireLessThan(double lessThanValue) {
		this.lessThanValue = lessThanValue;
	}
	
	/**
	 * Require field be even if requireEven true.
	 * @param requireEven 
	 */
	public void requireEven(Boolean requireEven) {
		this.requireEven = requireEven;
		this.requireOdd = null;
	}

	/**
	 * Require field be odd if requireOdd true.
	 * @param requireOdd
	 */
	public void requireOdd(Boolean requireOdd) {
		this.requireOdd = requireOdd;
		this.requireEven = null;
	}
	
	@Override
	public boolean execute(Object value, ModelValidator validator) {
		try {  
			if(this.requireInteger){
				Integer.parseInt((String)value);  
			}
			double num = Double.parseDouble((String)value);
			if(equalToValue != null && num != equalToValue){
				String val = equalToValue + "";
				if(requireInteger) val = equalToValue.intValue() + "";
				String message = this.rangeMessage.replace("%{type}", "equal to").replace("%{value}", val);
				validator.addError(getFieldKey(), getFieldName() + " " + message);
				return false;
			}
			if(greaterThanValue != null && num <= greaterThanValue){
				String val = greaterThanValue + "";
				if(requireInteger) val = greaterThanValue.intValue() + "";
				String message = this.rangeMessage.replace("%{type}", "greater than").replace("%{value}", val);
				validator.addError(getFieldKey(), getFieldName() + " " + message);
				return false;
			}
			if(greaterThanOrEqualToValue != null && num < greaterThanOrEqualToValue){
				String val = greaterThanOrEqualToValue + "";
				if(requireInteger) val = greaterThanOrEqualToValue.intValue() + "";
				String message = this.rangeMessage.replace("%{type}", "greater than or equal to").replace("%{value}", val);
				validator.addError(getFieldKey(), getFieldName() + " " + message);
				return false;
			}
			if(lessThanValue != null && num >= lessThanValue){
				String val = lessThanValue + "";
				if(requireInteger) val = lessThanValue.intValue() + "";
				String message = this.rangeMessage.replace("%{type}", "less than").replace("%{value}", val);
				validator.addError(getFieldKey(), getFieldName() + " " + message);
				return false;
			}
			if(lessThanOrEqualToValue != null && num > lessThanOrEqualToValue){
				String val = lessThanOrEqualToValue + "";
				if(requireInteger) val = lessThanOrEqualToValue.intValue() + "";
				String message = this.rangeMessage.replace("%{type}", "less than or equal to").replace("%{value}", val);
				validator.addError(getFieldKey(), getFieldName() + " " + message);
				return false;
			}
			if(requireEven != null && requireEven){
				int val = Integer.parseInt((String)value);
				if(val % 2 != 0){
					validator.addError(getFieldKey(), getFieldName() + " " + this.parityMessage.replace("%{parity}", "even"));
					return false;
				}
			}
			if(requireOdd != null && requireOdd){
				int val = Integer.parseInt((String)value);
				if(val % 2 == 0){
					validator.addError(getFieldKey(), getFieldName() + " " + this.parityMessage.replace("%{parity}", "odd"));
					return false;
				}
			}
		}catch(NumberFormatException nfe){  
			validator.addError(getFieldKey(), getFieldName() + " " + this.message);
			return false;
		}  
		return true;
	}

}
