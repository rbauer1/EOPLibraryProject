/**
 * COPYRIGHT 2014 Sandeep Mitra and students 
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. * 
 */
package userinterface;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * Provides a means of placing top-level components ONLY at specified locations
 * on the full screen
 */
public class WindowPosition {
	public static Point center = getCenter();

	/**
	 * Returns the coordinates of the center of the screen
	 * @return Point object indicating the coordinates of the center of the
	 *         screen
	 */
	public static Point getCenter() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		return new Point(d.width / 2, d.height / 2);
	}

	/**
	 * Used to place a component so that the center of the component is at the
	 * center of the screen
	 * @param c - Component to place at the center of the screen
	 */
	public static void placeCenter(Component c) {
		if (c != null) {
			Dimension d = c.getSize();
			c.setLocation(new Point(center.x - d.width / 2, center.y - d.height / 2));
		}
	}

	/**
	 * Used to place a component so that the top left corner of the component is
	 * at the top left corner of te screen
	 * @param c - Component to place at the top left corner of the screen
	 */
	public static void placeTopLeft(Component c) {
		if (c != null) {
			c.setLocation(new Point(0, 0));
		}
	}

	/**
	 * Used to place a component so that the top right corner of the component
	 * is at the top right corner of the screen
	 * @param c - Component to place at the top right corner of the screen
	 */
	public static void placeTopRight(Component c) {
		if (c != null) {
			Dimension d = c.getSize();
			c.setLocation(new Point(2 * center.x - d.width, 0));
		}
	}

	/**
	 * Used to place a component so that the bottom left corner of the component
	 * is at the bottom left corner of the screen
	 * @param c - Component to place at the bottom left corner of the screen
	 */
	public static void placeBottomLeft(Component c) {
		if (c != null) {
			Dimension d = c.getSize();
			c.setLocation(new Point(0, 2 * center.y - d.height));
		}
	}

	/**
	 * Used to place a component so that the bottom right corner of the
	 * component is at the bottom right corner of the screen
	 * @param c - Component to place at the bottom right corner of the screen
	 */
	public static void placeBottomRight(Component c) {
		if (c != null) {
			Dimension d = c.getSize();
			c.setLocation(new Point(2 * center.x - d.width, 2 * center.y - d.height));
		}
	}
}
