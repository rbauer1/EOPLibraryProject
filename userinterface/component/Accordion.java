package userinterface.component;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import userinterface.utilities.Utils;

public class Accordion extends JPanel {

	private static class ShowHideAction implements ActionListener {
		private final Accordion parent;
		private final JComponent content;

		public ShowHideAction(Accordion parent, JComponent content) {
			this.parent = parent;
			this.content = content;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JComponent opened = parent.getOpened();

			if (opened != null && opened != content) {
				opened.setVisible(false);
				parent.setOpened(null);
			}

			content.setVisible(!content.isVisible());

			if (content.isVisible()) {
				parent.setOpened(content);
			}
		}
	}

	private static final long serialVersionUID = 6669709465294376732L;
	private final JPanel dummy = new JPanel();
	private JComponent opened = null;

	private final GridBagConstraints cons = new GridBagConstraints();

	public Accordion() {
		setBorder(null);
		setLayout(new GridBagLayout());
		cons.gridx = 1;
		cons.ipadx = cons.ipady = 0;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.weightx = 1;
	}
	
	private void updateSize() {
		setWidth(getSize().width);
	}
	
	public void setWidth(int w) {
		int h = 0;
		for (Component c : getComponents()) {
			h += c.getHeight();
		}
		Utils.setAllSize(this, w, h);
	}

	public void add(AbstractButton comp, JComponent content) {
		GridBagConstraints cons_ = (GridBagConstraints)cons.clone();

		cons_.insets = new Insets(0, 20, 0, 0);
		comp.addActionListener(new ShowHideAction(this, content));
		content.setVisible(false);
		add(comp, cons);
		add(content, cons_);
		updateSize();
	}

	public void add(JComponent comp) {
		add(comp, cons);
		updateSize();
	}

	public void fit() {
		add(dummy);
		updateSize();
	}

	public JComponent getOpened() {
		return opened;
	}

	protected void setOpened(JComponent comp) {
		opened = comp;
	}

}
