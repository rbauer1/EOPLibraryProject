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

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PhoneField extends Panel implements FormField {

	private static final long serialVersionUID = 808393114307875546L;
	
	private NumericTextField phone1;
	private NumericTextField phone2;
	private NumericTextField phone3;
	
	public PhoneField(){
		super(new FlowLayout(FlowLayout.LEFT));
		build();
	}

	@Override
	public String getValue() {
		return phone1.getValue() + "-" + phone2.getValue() + "-" + phone3.getValue();
	}

	@Override
	public void setValue(String value) {
		value = value.replaceAll("[^\\d]", "");
		if(value.length() == 10){
			phone1.setValue(value.substring(0, 3));
			phone2.setValue(value.substring(3, 6));
			phone3.setValue(value.substring(6, 10));
		}else{
			phone1.reset();
			phone2.reset();
			phone3.reset();
		}
	}

	@Override
	public void reset() {
		phone1.reset();
		phone2.reset();
		phone3.reset();
	}

	private void build(){
		phone1 = new NumericTextField(3,3);
		phone1.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
		add(phone1);
		
		add(new JLabel(" - "));
		
		phone2 = new NumericTextField(3,3);
		phone2.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
		add(phone2);
		
		add(new JLabel(" - "));
		
		phone3 = new NumericTextField(4,4);
		phone3.getDocument().addDocumentListener(new PhoneFieldDocumentListener());
		add(phone3);
	}

	@Override
	public boolean requestFocusInWindow(){
		return phone1.requestFocusInWindow();
	}
	
	private class PhoneFieldDocumentListener implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {}

		public void removeUpdate(DocumentEvent e) {}

		public void insertUpdate(DocumentEvent e) {
			if (phone1.getText().length() == 3 && phone2.getText().length() == 0) {
				phone2.requestFocus(true);
			}

			if (phone2.getText().length() == 3 	&& phone3.getText().length() == 0) {
				phone3.requestFocus(true);
			}
		}
	}
	
	
}
