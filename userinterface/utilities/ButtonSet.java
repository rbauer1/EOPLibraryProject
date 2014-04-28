package userinterface.utilities;

import userinterface.component.flat.FButton;

public class ButtonSet {
	
	private FButton opened = null;
	
	// TODO use interafce for buttons ?
	public void register(FButton b) {
		if (opened != null && b != opened) {
			opened.unpress();
			opened = null;
		}
		opened = b;
	}
	
	public void unregister(FButton b) {
		if (opened == b)
			opened = null;
	}

}
