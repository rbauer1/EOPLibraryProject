/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

/**
 * Interface that represents the Model object in MVC architecture
 */
public interface IModel {
	
	/**
	 * Retrieves state data from model
	 * @param key
	 * @return value referenced by key
	 */
	public Object getState(String key);

	/**
	 * Subscribe View for updates from model events.
	 * @param key
	 * @param subscriber
	 */
	public void subscribe(String key, IView subscriber);

	/**
	 * UnSubscribe View from updates from model events.
	 * @param key
	 * @param subscriber
	 */
	public void unSubscribe(String key, IView subscriber);

	/**
	 * Handles changes to model state.
	 * @param key
	 * @param value
	 */
	public void stateChangeRequest(String key, Object value);
}
