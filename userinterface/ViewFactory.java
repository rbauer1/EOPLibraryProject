//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and students 
//    The College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
package userinterface;

import impresario.IModel;

public class ViewFactory {

	public static View createView(String viewName, IModel model) {
		if (viewName.equals("LoginView") == true) {
			return new LoginView(model);
		}
		return null;
	}
}
