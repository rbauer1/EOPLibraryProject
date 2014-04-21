package controller.transaction;

import controller.Controller;

/**
 * Created by chaber_e on 14/04/2014.
 * For Package controller.transaction
 */
public class GeneratePDFTransaction extends Transaction {
	/**
	 * Constructs a transaction with the provided controller as a parent.
	 * parentController can be another transaction or a main controller.
	 *
	 * @param parentController
	 */
	protected GeneratePDFTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute() {
		showView("generatePDFView");
	}

	@Override
	public Object getState(String key) {
		return null;
	}

	@Override
	public void stateChangeRequest(String key, Object value) {

	}
}
