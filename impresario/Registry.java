/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to instantiate the object that is encapsulated by every
 * Observer client in order to keep track of which control subscribes to which
 * key and which keys depend on which other keys. After the client updates its
 * state on the basis of a posted state change, this class' methods are used to
 * update the GUI controls that subscribe to the keys that depend on the key on
 * which the state change is posted.
 */
public abstract class Registry<T> {

	/** map of lists of subscribers for each event */
	protected Map<String, List<T>> subscribers;

	/** class this registry is being used in */
	protected String className;

	/**
	 * Constructs a registry
	 * @param classname
	 */
	public Registry(String classname) {
		className = classname;
		subscribers = new HashMap<String, List<T>>();
	}

	/**
	 * Subscribes a object (Model/View) to a key. After this method executes,
	 * the Registry object knows that the subscriber object is associated with
	 * the key, and when an update to the key must be invoked, the subscriber
	 * object is one of the actual objects whose update method must be called
	 * @param key - the event the subscriber object wants to subscribe to
	 * @param subscriber - Object that wishes to subscribe to the key
	 */
	public void subscribe(String key, T subscriber) {
		List<T> keySubscribers = subscribers.get(key);
		if (keySubscribers == null) {
			keySubscribers = new ArrayList<T>();
			subscribers.put(key, keySubscribers);
		}
		keySubscribers.add(subscriber);
	}

	/**
	 * Unsubscribe the provided subscriber object from the key
	 * @param key - Key value to unsubscribe the subscriber from
	 * @param subscriber - Object to unsubscribe from key
	 */
	public void unSubscribe(String key, T subscriber) {
		if (subscribers.containsKey(key)) {
			subscribers.get(key).remove(subscriber);
		}
	}

}
