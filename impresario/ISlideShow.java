/**
 * COPYRIGHT 2003 ArchSynergy, Ltd. - ALL RIGHTS RESERVED
 * This file is the product of ArchSynergy, Ltd. and cannot be 
 * reproduced, copied, or used in any shape or form without 
 * the express written consent of ArchSynergy, Ltd.
 */
package impresario;

/**
 * The interface for the entity that is responsible for switching views in an
 * MVC setup.
 */
public interface ISlideShow {

	/**
	 * Shows the provided view
	 * @param view
	 */
	public void swapToView(IView view);
}
