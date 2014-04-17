/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

/**
 * Interface that represents the control object in MVC architecture
 */
public interface IControl {

	/**
	 * Subscribe model to event of control object
	 * @param key
	 * @param subscriber
	 */
	public void subscribe(String key, IModel subscriber);

	/**
	 * UnSubscribe model from event of control object
	 * @param key
	 * @param subscriber
	 */
	public void unSubscribe(String key, IModel subscriber);
}
