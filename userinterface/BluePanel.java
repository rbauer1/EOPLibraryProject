//************************************************************
//    COPYRIGHT Sandeep Mitra and Students, The College at Brockport, 2013 - ALL RIGHTS RESERVED
//
// This file is the product of Sandeep Mitra and Students, 
// The College at Brockport, and is
// provided for unrestricted use provided that this legend
// is included on all tape media and as part of the software
// program in whole or part. Users may copy or modify this
// file without charge, but are not authorized to license or
// distribute it to anyone else except as part of a product or
// program developed by the user.
//*************************************************************
package userinterface;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class BluePanel extends JPanel {
	private static final long serialVersionUID = 3274303070952112811L;

	public BluePanel() {
		setBackground(new Color(133, 195, 230));
	}
	
	public BluePanel(LayoutManager lm){
		super(lm);
	}
}
