package userinterface.screen;

import impresario.IView;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JPanel;

import userinterface.MainFrame;
import userinterface.view.View;
import controller.Controller;

public abstract class Screen extends JPanel implements ActionListener {
	
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
