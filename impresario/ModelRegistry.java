/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

import java.util.List;
import java.util.Properties;

import common.PropertyFile;

/**
 * This class is used to instantiate the object that is encapsulated by every
 * Observer client in order to keep track of which control subscribes to which
 * key and which keys depend on which other keys. After the client updates its
 * state on the basis of a posted state change, this class' methods are used to
 * update the GUI controls that subscribe to the keys that depend on the key on
 * which the state change is posted.
 */
public class ModelRegistry extends Registry<IView> {

	/** map of keys that are dependent on other keys */
	private Properties dependencies;


	/**
	 * Construct Model registry with dependencies
	 * @param classname
	 * @param dependencies
	 */
	public ModelRegistry(String classname, Properties dependencies) {
		super(classname);
		this.dependencies = dependencies;
	}

	/**
	 * Construct Model registry with dependencies from file
	 * @param classname
	 * @param dependencyFile
	 */
	public ModelRegistry(String classname, String dependencyFile){
		super(classname);
		dependencies = new PropertyFile(dependencyFile);
	}

	/**
	 * Construct Model registry with empty dependencies
	 * @param classname
	 */
	public ModelRegistry(String classname){
		super(classname);
		dependencies = new Properties();
	}

	/**
	 * Sets the model registry key dependencies
	 * @param dependencies
	 */
	public void setDependencies(Properties dependencies){
		this.dependencies = dependencies;
	}

	/**
	 * Calls the update method for all Views that are subscribed to keys 
	 * dependent to the key provided. Uses the getState method of the provided 
	 * model to retrieve the value passed to the Views.
	 * @param key - Value of key on which the state change was posted and whose
	 *            dependencies must be determined
	 * @param model
	 */
	public void updateSubscribers(String key, IModel model) {
		String keys = key;
		if(dependencies.getProperty(key) != null){
			keys += "," + dependencies.getProperty(key);
		}

		String[] events = keys.split(",");
		for(String event : events){
			List<IView> eventSubscribers = subscribers.get(event);
			if(eventSubscribers != null){
				for(IView subscriber : eventSubscribers){
					subscriber.updateState(event, model.getState(event));
				}
			}
		}
	}

}