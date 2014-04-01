package model.validation;

/**
 * Model Validation that ensures the field is a banner id
 */
public class BannerIdValidation extends FormatValidation {
	
	private static final String BANNER_ID_REGEX = "^800\\d{6}$";

	/**
	 * Constructs a validation that ensures the field is a banner id.
	 * @param fieldKey
	 * @param fieldName
	 * @param message
	 */
	public BannerIdValidation(String fieldKey, String fieldName, String message) {
		super(fieldKey, fieldName, BANNER_ID_REGEX, message);
	}

	/**
	 * Constructs a validation that ensures the field is a banner id.
	 * Default message is "fieldName must be a valid Banner Id"
	 * @param fieldKey
	 * @param fieldName
	 */
	public BannerIdValidation(String fieldKey, String fieldName) {
		this(fieldKey, fieldName, "must be a valid Banner Id");
	}

}
