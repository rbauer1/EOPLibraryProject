package userinterface.menu;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import userinterface.component.Panel;
import userinterface.utilities.Utils;
import userinterface.view.panel.MenuPanel;

public class MenuArrow extends JPanel {

	private static final long serialVersionUID = -1835881739788628399L;
	private static final int ARROW_WIDTH = 40;
	private static final int ARROW_HEIGHT = 60;

	private JComponent ref = null;

	public MenuArrow(JComponent comp) {
		ref = comp;

		Utils.setAllSize(this, ARROW_WIDTH, ARROW_HEIGHT);
		comp.add(this);

		comp.setLayout(null);
		//Dimension dimMenu = MainFrame.getInstance().getMenu().getPreferredSize();
		Dimension dimCont = comp.getPreferredSize();
		Dimension dimArrow = getPreferredSize();

		// Insets ins = getInsets();

		setBounds(
				// FIXME: The insets seems to make the new coordinates wrong
				/* ins.left + */ MenuPanel.WIDTH - dimArrow.width / 2,
				/* ins.top + */ dimCont.height / 2 - dimArrow.height / 2,
				ARROW_WIDTH,
				ARROW_HEIGHT);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension d = getPreferredSize();

		g.setColor(Panel.BACKGROUND_COLOR);
		g.fillPolygon(
				new int[] {0, d.width, d.width},
				new int[] {d.height / 2, 0, d.height},
				3
				);
	}

	public void remove() {
		ref.remove(this);
	}
}
