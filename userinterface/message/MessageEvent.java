package userinterface.message;

import java.util.ArrayList;
import java.util.List;

/**
 * This object holds all the information corresponding to the display of a message in the message panel.
 * Holds the title and type, and optionally a list of messages.
 */
public final class MessageEvent {
	
	/** Main message of the message */
	private final String title;
	
	/** Type of message - can be Success, Error, Info, Warning */
	private final MessageType type;
	
	/** List of sub messages */
	private final List<String> messages;

	/**
	 * @param type
	 * @param title
	 * @param messages
	 */
	public MessageEvent(MessageType type, String title, List<String> messages) {
		this.title = title;
		this.type = type;
		this.messages = messages;
	}

	/**
	 * @param type
	 * @param title
	 */
	public MessageEvent(MessageType type, String title) {
		this(type, title, new ArrayList<String>());
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return type - Success, Error, Info, Warning
	 */
	public MessageType getType() {
		return type;
	}

	/**
	 * @return messages
	 */
	public List<String> getMessages() {
		return messages;
	}	
}
