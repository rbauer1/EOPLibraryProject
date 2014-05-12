package userinterface.screen;

import impresario.IView;

import javax.swing.JPanel;

import userinterface.view.View;

public abstract class Screen extends JPanel implements IView {

	private static final long serialVersionUID = -1353895090405042658L;
	protected View view = null;

	public Screen(View view) {
		this.view = view;
	}

	protected abstract void addView(View comp);

	public void clearView() {
		if(view != null){
			remove(view);
		}
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		clearView();
		this.view = view;
		addView(view);
	}
}
