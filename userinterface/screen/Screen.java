package userinterface.screen;

import impresario.IView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import userinterface.view.View;

public abstract class Screen extends JPanel implements ActionListener, IView {

	private static final long serialVersionUID = -1353895090405042658L;
	private View view = null;
	
	public Screen(View view) {
		this.view = view;
	}
	
	public View getView() {
		return view;
	}
	
	public void setView(View view) {
		this.view = view;
		clearView();
		addView(view);
	}
	
	public abstract void processAction(Object source);

	public void actionPerformed(ActionEvent event) {
		processAction(event.getSource());
	}
	
	public abstract void clearView();
	protected abstract void addView(View comp);
}
