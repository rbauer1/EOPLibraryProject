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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import event.Event;

/**
 * Factory Class for creating transactions.
 */
public class MessageFactory {
	
	private static final String MESSAGE_PACKAGE = "userinterface.message";
	
	/**
	 * Private Constructor so that this class can't be instantiated.
	 */
	private MessageFactory(){}
	
	/**
	 * Creates a message
	 * @param type
	 * @param title
	 * @param messages
	 * @return Message of provided type
	 */
	public static Message createMessage(MessageType type, String title, List<String> messages){
		try {
			Class<?> clazz = Class.forName(MESSAGE_PACKAGE + "." + type + "Message");
			if(messages == null){
				Constructor<?> ctor = clazz.getConstructor(String.class);
				return (Message) ctor.newInstance(title);
			}
			Constructor<?> ctor = clazz.getConstructor(String.class, List.class);
			return (Message) ctor.newInstance(title, messages);
		} catch (ClassNotFoundException e) {
			new Event("MessageFactory", "createTransaction", "Invalid message type: " + type, Event.FATAL);
		} catch (InstantiationException e) {
			new Event("MessageFactory", "createTransaction", "Error occured while instantiating message.", Event.FATAL);
		} catch (IllegalAccessException e) {
			new Event("MessageFactory", "createTransaction", "Illegal Access for provided message type.", Event.FATAL);
		} catch (NoSuchMethodException e) {
			new Event("MessageFactory", "createTransaction", "Constructor does not exist in provided message type.", Event.FATAL);
		} catch (SecurityException e) {
			new Event("MessageFactory", "createTransaction", "Invalid security for constructor in message type. ", Event.FATAL);
		} catch (IllegalArgumentException e) {
			new Event("MessageFactory", "createTransaction", "Illegal argument pass to message constructor.", Event.FATAL);
		} catch (InvocationTargetException e) {
			new Event("MessageFactory", "createTransaction", "Exception thrown by message constructor.", Event.FATAL);
		}
		throw new IllegalArgumentException("Invalid message type");
	}
	
	/**
	 * Creates message
	 * @param type
	 * @param title
	 * @return message of provided type
	 */
	public static Message createMessage(MessageType type, String title){
		return createMessage(type, title, null);
	}

	/**
	 * Creates message for event provided
	 * @param messageEvent
	 * @return message for provided event
	 */
	public static Message createMessage(MessageEvent messageEvent) {
		return createMessage(messageEvent.getType(), messageEvent.getTitle(), messageEvent.getMessages());
	}
}
