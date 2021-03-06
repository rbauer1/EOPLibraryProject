/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package userinterface.view.form;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import userinterface.component.FormField;
import userinterface.component.Panel;
import userinterface.view.View;

public abstract class Form extends Panel implements ActionListener{

	private static final long serialVersionUID = 4224730803989222451L;

	/** Font used for general items */
	public static final Font NORMAL_FONT = new Font("Arial", Font.TYPE1_FONT, 14);

	/** Border color for fields */
	public static final Color FIELD_BORDER_COLOR = Color.black;

	protected Map<String, FormField> fields;
	protected FormField defaultFocusField;
	protected View view;

	protected Form(View view){
		this.view = view;
		fields = new HashMap<String, FormField>();
		setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		build();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		view.processAction(this);
	}

	protected void addField(String name, FormField field){
		if(fields.isEmpty()){
			defaultFocusField = field;
		}
		fields.put(name, field);
	}

	protected abstract void build();

	protected Properties filterValues(Properties values){
		return values;
	}

	public FormField get(String name){
		return fields.get(name);
	}

	public Properties getNonEmptyValues(){
		Properties values = new Properties();
		for(String key : fields.keySet()){
			String value = fields.get(key).getValue();
			if(value != null && value.length() > 0){
				values.setProperty(key, value);
			}
		}
		return filterValues(values);
	}

	public Properties getValues(){
		Properties values = new Properties();
		for(String key : fields.keySet()){
			String value = fields.get(key).getValue();
			if(value != null){
				values.setProperty(key, value);
			}
		}
		return filterValues(values);
	}

	public boolean requestFocusForDefaultField(){
		return defaultFocusField.requestFocusInWindow();
	}

	public void reset(){
		for(FormField value : fields.values()){
			value.reset();
		}
	}

	public void setAllFieldsEnabled(boolean enabled){
		for(FormField field : fields.values()){
			field.setEnabled(enabled);
		}
	}

	public void setFieldEnabled(String key, boolean enabled){
		fields.get(key).setEnabled(enabled);
	}

	public void setValues(Properties values){
		reset();
		updateValues(values);
	}

	public void updateValues(Properties values){
		for (String key : values.stringPropertyNames()) {
			if(fields.containsKey(key)){
				fields.get(key).setValue(values.getProperty(key));
			}
		}
	}

}
