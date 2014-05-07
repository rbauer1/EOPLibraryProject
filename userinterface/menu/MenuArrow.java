package userinterface.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import userinterface.Config;
import userinterface.MainFrame;
import userinterface.panel.MenuPanel;
import userinterface.utilities.Utils;

public class MenuArrow extends JPanel {
	
	private JComponent ref = null;

	public void remove() {
		ref.remove(this);
	}
	
	public MenuArrow(JComponent comp, Integer w, Integer h) {
		ref = comp;

		Utils.setAllSize(this, w, h);
		comp.add(this);
		
		comp.setLayout(null);
		//Dimension dimMenu = MainFrame.getInstance().getMenu().getPreferredSize();
		Dimension dimCont = comp.getPreferredSize();
		Dimension dimArrow = this.getPreferredSize();
		
		// Insets ins = getInsets();

		setBounds(
				// FIXME: The insets seems to make the new coordinates wrong
				/* ins.left + */ MenuPanel.WIDTH - (dimArrow.width / 2),
				/* ins.top + */ (dimCont.height / 2) - (dimArrow.height / 2),
				w,
				h);
	}

	protected void paintComponent(Graphics g) {
		Dimension d = getPreferredSize();

		g.setColor(Config.VIEW_BACKGROUND);
		g.fillPolygon(
				(new int[] {0, d.width, d.width}),
				(new int[] {d.height / 2, 0, d.height}),
				3
				);
	}
}
