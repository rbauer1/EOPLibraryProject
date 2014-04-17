/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PhoneField extends Panel implements FormField {

	private static final long serialVersionUID = 808393114307875546L;
	
	private NumericTextField phone1Field;
	private NumericTextField phone2Field;
	private NumericTextField phone3Field;
	
	public PhoneField(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));		
		build();
	}

	@Override
	public String getValue() {
		// Using underscores for missing digits because underscore is wildward in sql like condition
		final String underscores = "____";
		String phone1 = phone1Field.getValue();
		phone1 += underscores.substring(phone1.length(), 3);
		String phone2 = phone2Field.getValue();
		phone2 += underscores.substring(phone2.length(), 3);
		String phone3 = phone3Field.getValue();
		phone3 += underscores.substring(phone3.length(), 4);
		String phone = phone1 + "-" + phone2 + "-" + phone3;
		//return empty string if empty
		return phone.equals("___-___-____") ? "" : phone;
	}

	@Override
	public void setValue(String value) {
		value = value.replaceAll("[^\\d]", "");
		if(value.length() == 10){
			phone1Field.setValue(value.substring(0, 3));
			phone2Field.setValue(value.substring(3, 6));
			phone3Field.setValue(value.substring(6, 10));
		}else{
			phone1Field.reset();
			phone2Field.reset();
			phone3Field.reset();
		}
	}

	@Override
	public void reset() {
		phone1Field.reset();
		phone2Field.reset();
		phone3Field.reset();
	}
	
	@Override
	public void setEnabled(boolean enabled){
		phone1Field.setEnabled(enabled);
		phone2Field.setEnabled(enabled);
		phone3Field.setEnabled(enabled);
	}

	private void build(){
		phone1Field = new NumericTextField(3,3);
		phone1Field.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
		phone1Field.addActionListener(new PhoneFieldActionListener());
		add(phone1Field);
		
		add(new JLabel(" - "));
		
		phone2Field = new NumericTextField(3,3);
		phone2Field.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
		phone2Field.addActionListener(new PhoneFieldActionListener());
		add(phone2Field);
		
		add(new JLabel(" - "));
		
		phone3Field = new NumericTextField(4,4);
		phone3Field.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
		phone3Field.addActionListener(new PhoneFieldActionListener());
		add(phone3Field);
		
	}

	@Override
	public boolean requestFocusInWindow(){
		return phone1Field.requestFocusInWindow();
	}
		
	public void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	public void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}
	
	protected void fireActionPerformed(ActionEvent e) {
	    Object[] listeners = listenerList.getListenerList();

	    for (int i = listeners.length - 2; i >= 0; i -= 2) {
	        if (listeners[i] == ActionListener.class) {
	            ((ActionListener)listeners[i + 1]).actionPerformed(e);
	        }
	    }
	}

	public ActionListener[] getActionListeners() {
		return (ActionListener[]) listenerList.getListeners(ActionListener.class);
	}	
	
	private class PhoneFieldActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			fireActionPerformed(e);
		}

	}
	
	private class PhoneFieldDocumentListener implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {}

		public void removeUpdate(DocumentEvent e) {}

		public void insertUpdate(DocumentEvent e) {
			if (phone1Field.getText().length() == 3 && phone2Field.getText().length() == 0) {
				phone2Field.requestFocus(true);
			}

			if (phone2Field.getText().length() == 3 	&& phone3Field.getText().length() == 0) {
				phone3Field.requestFocus(true);
			}
		}
	}
	
	
}
