package userinterface.message;

/**
 * Defines the possible types for a message
 */
public enum MessageType {
	ERROR("Error"), SUCCESS("Success"), WARNING("Warning"), INFO("Info");
	
	private String value;
	
	MessageType(String value){
		this.value = value;
	}
	
	public String toString(){
		return value;
	}
}
