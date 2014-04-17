/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface.message;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import userinterface.component.Panel;

/**
 * Super class for all types of messages
 */
public abstract class Message extends Panel {
	
	private static final long serialVersionUID = 5719958237714620678L;

	/** Title of the message */
	protected String title;
	
	/** List of messages to be displayed under title */
	protected List<String> messages;
	
	/** Display box for label */
	protected JLabel label;
	
	/**
	 * Constructs message from provided title and messages
	 * @param title
	 * @param messages
	 */
	public Message(String title, List<String> messages){
		super(new FlowLayout(FlowLayout.LEFT));
		this.title = title;
		this.messages = messages;
		this.label = new JLabel();

		add(label);
		format();
		build();
	}
	
	/**
	 * Constructs message from title
	 * @param title
	 */
	public Message(String title){
		this(title, new ArrayList<String>());
	}
	
	/**
	 * Formats label panel
	 */
	protected abstract void format();
	
	/**
	 * Builds text for label from title and messages
	 */
	protected void build(){
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append(title);
		if(messages.size() > 0){
			sb.append("<ul style='margin-left: 20px; margin-bottom: 0px;'>");
			for(String message : messages){
				sb.append("<li>");	
				sb.append(message);	
				sb.append("</li>");	
			}
			sb.append("</ul>");
		}
		sb.append("</html>");
		label.setText(sb.toString());
	}
}
