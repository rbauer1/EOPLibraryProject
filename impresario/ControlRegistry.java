/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

import java.util.List;

/**
 * This class is used to instantiate the object that is encapsulated by every
 * Observer client in order to keep track of which control subscribes to which
 * key and which keys depend on which other keys. After the client updates its
 * state on the basis of a posted state change, this class' methods are used to
 * update the GUI controls that subscribe to the keys that depend on the key on
 * which the state change is posted.
 */
public class ControlRegistry extends Registry<IModel> {

	/**
	 * Construct new Registry
	 * @param classname
	 */
	public ControlRegistry(String classname) {
		super(classname); // construct our base class
	}

	/**
	 * This method invokes the stateChangeRequest method on all IModels that
	 * have subscribed for the specified key.
	 * @param key - Key on which the state change occurred
	 * @param value - value of the key that has changed
	 */
	public void updateSubscribers(String key, Object value) {
		List<IModel> keySubscribers = subscribers.get(key);
		
		if(keySubscribers != null){
			for(IModel subscriber : keySubscribers){
				subscriber.stateChangeRequest(key, value);
			}
		}
	}
}