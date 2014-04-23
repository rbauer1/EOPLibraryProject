package userinterface.menu;

import impresario.IView;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.util.Map;

import controller.Controller;
import userinterface.component.Accordion;
import userinterface.component.Button;
import userinterface.message.MessagePanel;

public abstract class Menu implements ActionListener {
	
	/** Controller that this view belongs to */
	protected Controller controller;
	
	private JButton parent;
	private Accordion content = new Accordion();
	
	public static final Color BACKGROUND_COLOR = new Color(0xF0F0E8);
	public static final Color BANNER_COLOR = new Color(0xE2E2D4);
	public static final Color SEPARATOR_COLOR = new Color(0x668D3C);
	public static final Color TITLE_COLOR = new Color(0x668D3C).darker();
	public static final String FONT_NAME = "Garamond";
	public static final int GENERAL_FONT_SIZE = 16;
	public static final int TITLE_FONT_SIZE = GENERAL_FONT_SIZE + 2;
	protected static final Font GENERAL_FONT = new Font(FONT_NAME, Font.TYPE1_FONT, GENERAL_FONT_SIZE);

	protected Menu(Controller controller, String title) {
		this.controller = controller;
		this.controller = controller;
		System.out.println("build");

		parent = new Button(title);
		System.out.println("build");

		build();
		
		System.out.println("build");
	}
	
	public JButton getParent() {
		return parent;
	}
	
	public Accordion getContent() {
		return content;
	}
	
	public abstract void processAction(Object source);

	public void actionPerformed(ActionEvent event) {
		processAction(event.getSource());
	}
	
	protected abstract void build();
}
