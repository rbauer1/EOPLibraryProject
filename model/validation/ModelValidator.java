package model.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Model;

/**
 * Object that runs the validations for a model.
 */
public class ModelValidator {
	
	/** The model this validator is used on */
	private Model model;
	
	/** HashMap of errors. Keys correspond to model fields */
	private Map<String, List<String>> errors;
	
	/** List of validations to be executed on this model */
	private Map<String, List<Validation>> validations;
	
	/**
	 * Constructs a validator for the provided model.
	 * @param model
	 */
	public ModelValidator(Model model){
		this.model = model;
		this.errors = new HashMap<String, List<String>>();;
		this.validations = new HashMap<String, List<Validation>>();		
	}
	
	/**
	 * Executes all the validations on the model. Clears all existing errors.
	 * @return true if the model is valid
	 */
	public boolean validate(){
		clearErrors();
		boolean valid = true;
		for(String key : this.validations.keySet()){
			valid &= validate(key);
		}
		System.out.println(getErrors());
		return valid;
	}
	
	/**
	 * Execute validations for the provided fieldKey
	 * @param fieldKey
	 * @return true if valid
	 */
	public boolean validate(String fieldKey){
		List<Validation> validations = this.validations.get(fieldKey);
		boolean valid = true;
		errors.put(fieldKey, new ArrayList<String>());
		if(validations != null){
			for(Validation validation : validations){
				valid &= validation.execute(model.getState(fieldKey), this);
			}
		}
		return valid;
	}
	
	/**
	 * Returns the model for the validator.
	 * @return model
	 */
	public Model getModel(){
		return this.model;
	}
	
	/**
	 * Add validation to be executed.
	 * @param validation
	 */
	public void addValidation(Validation validation){
		String key = validation.getFieldKey();
		List<Validation> validations = this.validations.get(key);
		if(validations == null){
			validations = new ArrayList<Validation>();
			this.validations.put(key, validations);
		}
		validations.add(validation);
	}
	
	/**
	 * Remove validation from being executed
	 * @param validation
	 */
	public void removeValidation(Validation validation){
		String key = validation.getFieldKey();
		List<Validation> validations = this.validations.get(key);
		if(validations != null){
			validations.remove(validation);
		}
	}
	
	/**
	 * Returns the map of errors for the model.
	 * Be sure to run validate before calling this method.
	 * @return map of errors
	 */
	public Map<String, List<String>> getErrors(){
		return errors;
	}
	
	/**
	 * Returns the map of errors for provided fieldKey.
	 * Be sure to call validate before calling this method.
	 * @return list of errors
	 */
	public List<String> getErrors(String fieldKey){
		List<String> errors = this.errors.get(fieldKey);
		if(errors == null){
			errors = new ArrayList<String>();
			this.errors.put(fieldKey, errors);
		}
		return errors;
	}
	
	/**
	 * Add error message for provided key.
	 * @param key
	 * @param message
	 */
	public void addError(String key, String message){
		if(!errors.containsKey(key)){
			errors.put(key, new ArrayList<String>());
		}
		errors.get(key).add(message);
	}
	
	/**
	 * Clears all errors
	 */
	private void clearErrors(){
		errors = new HashMap<String, List<String>>();
	}
}
