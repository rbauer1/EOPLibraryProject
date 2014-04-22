/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package userinterface;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import userinterface.component.Button;
import userinterface.component.Label;
import userinterface.component.Panel;
import userinterface.view.form.Form;

public class ViewHelper {

	public static JPanel createPlaceHolder(){
		Panel panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		panel.add(new Label(""));
		return panel;
	}

	public static JPanel formatButton(Button button) {
		Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		panel.add(button);
		return panel;
	}

	public static JPanel formatCenter(JComponent component) {
		return formatCenter(component, 5);
	}

	public static JPanel formatCenter(JComponent component, int margin) {
		Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
		panel.setBorder(BorderFactory.createEmptyBorder(margin,margin,margin,margin));
		panel.add(component);
		return panel;
	}

	public static JPanel formatCurrencyField(String labelText, JComponent component, LayoutManager layout) {
		Panel panel = new Panel(layout);

		Label label = new Label(labelText);
		final Dimension size = new Dimension(135, 30);
		label.setPreferredSize(size);
		panel.add(label);
		JLabel dollarLabel = new JLabel("$");
		dollarLabel.setFont(Form.NORMAL_FONT);
		panel.add(dollarLabel);


		panel.add(component);
		return panel;
	}

	public static JPanel formatCurrencyFieldCenter(String labelText, JComponent component) {
		return formatCurrencyField(labelText, component, new FlowLayout(FlowLayout.LEFT));
	}

	public static JPanel formatCurrencyFieldLeft(String labelText, JComponent component) {
		return formatCurrencyField(labelText, component, new FlowLayout(FlowLayout.LEFT));
	}

	public static JPanel formatField(String label, JComponent component, LayoutManager layout) {
		Panel panel = new Panel(layout);
		panel.add(new Label(label));
		panel.add(component);
		return panel;
	}

	public static JPanel formatFieldCenter(String label, JComponent component) {
		return formatField(label, component, new FlowLayout(FlowLayout.CENTER));
	}

	public static JPanel formatFieldLeft(String label, JComponent component) {
		return formatField(label, component, new FlowLayout(FlowLayout.LEFT));
	}

	public static JPanel formatLeft(JComponent component, int margin) {
		Panel panel = new Panel(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(BorderFactory.createEmptyBorder(margin,margin,margin,margin));
		panel.add(component);
		return panel;
	}

	public static JPanel formatTextAreaField(String label, JTextArea textArea, LayoutManager layout) {
		Panel panel = new Panel(layout);
		panel.add(new Label(label));
		JScrollPane scrollPane =  new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setAlignmentX(JScrollPane.CENTER_ALIGNMENT);
		scrollPane.setBorder(new LineBorder(Form.FIELD_BORDER_COLOR, 1));
		scrollPane.setPreferredSize(new Dimension( 400, 100 ));
		panel.add(scrollPane);
		return panel;
	}

	public static JPanel formatTextAreaFieldCenter(String label, JTextArea textArea) {
		return formatTextAreaField(label, textArea, new FlowLayout(FlowLayout.LEFT));
	}

	public static JPanel formatTextAreaFieldLeft(String label, JTextArea textArea) {
		return formatTextAreaField(label, textArea, new FlowLayout(FlowLayout.LEFT));
	}

	private ViewHelper() {}

}
