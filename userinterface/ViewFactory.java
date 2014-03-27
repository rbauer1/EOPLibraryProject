/************************************************************
	COPYRIGHT 2014 Sandeep Mitra and students:
		Riley Bauer, Matt Andre, Etienne Chabert, Charly Chevalier, Matthew Slomski, James Howe

    The College at Brockport, State University of New York. -
	  ALL RIGHTS RESERVED

	This file is the product of The College at Brockport and cannot
	be reproduced, copied, or used in any shape or form without
	the express written consent of The College at Brockport.
************************************************************/
package userinterface;

import impresario.IModel;

import java.lang.reflect.InvocationTargetException;

public class ViewFactory {
	public static final String UI_PACKAGE = "userinterface";
	public static View createView(String className, IModel model) {
		try {
			return (View)Class.forName(UI_PACKAGE+"."+className).
					getConstructor(IModel.class).newInstance(model);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
