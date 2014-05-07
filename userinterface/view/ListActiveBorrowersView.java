package userinterface.view;

import userinterface.view.form.ActiveBorrowerSearchForm;
import controller.Controller;

public class ListActiveBorrowersView extends ListBorrowersView {

	private static final long serialVersionUID = 2428945140614820275L;

	public ListActiveBorrowersView(Controller controller) {
		super(controller);
	}

	@Override
	protected void buildForm() {
		form = new ActiveBorrowerSearchForm(this);
		add(form);
	}

}
