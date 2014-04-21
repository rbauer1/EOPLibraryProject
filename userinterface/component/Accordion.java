package userinterface.component;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Accordion extends JPanel {
	
	private JPanel dummy = new JPanel();
	private JComponent opened = null;
	private GridBagConstraints cons = new GridBagConstraints();

	private static class ShowHideAction implements ActionListener {
		private Accordion parent;
		private JComponent content;

		public ShowHideAction(Accordion parent, JComponent content) {
			this.parent = parent;
			this.content = content;
		}
		
		public void actionPerformed(ActionEvent e) { 
			JComponent opened = parent.getOpened();

			if (opened != null && opened != content) {
				opened.setVisible(false);
				parent.setOpened(null);
			}

			content.setVisible(!content.isVisible());
			
			if (content.isVisible())
				parent.setOpened(content);
		}
	}
	
	public Accordion() {
		setBorder(null);
		setLayout(new GridBagLayout());
		cons.gridx = 1;
		cons.ipadx = cons.ipady = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.weightx = 1;
	}
	
	public JComponent getOpened() {
		return opened;
	}
	
	protected void setOpened(JComponent comp) {
		opened = comp;
	}
	
	public void add(AbstractButton comp, JComponent content) {
		GridBagConstraints cons_ = (GridBagConstraints)cons.clone();
		
		cons_.insets = new Insets(0, 20, 0, 0);
		comp.addActionListener(new ShowHideAction(this, content));
		content.setVisible(false);
		add(comp, cons);
		add(content, cons_);
	}
	
	public void add(JComponent comp) {
		add(comp, cons);
	}
	
	public void fit() {
		add(dummy);
	}
	
}
