/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

/**
 * The Interface for View object in MVC architecture.
 */
public interface IView {
	
	/**
	 * Called by model this subscribed to handle updates
	 * @param key
	 * @param value
	 */
	public void updateState(String key, Object value);
}
