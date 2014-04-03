// tabs=4
//************************************************************
//	COPYRIGHT 2014 Sandeep Mitra and Students, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
//
// Specify the package
package userinterface;

// system imports
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

//============================================================
public class LogoPanel extends JPanel
{
	private static final long serialVersionUID = 437901299835092005L;

	//----------------------------------------------------------
	// Construct the Logo Panel once in the beginning of the
	// program run. It is instantiated by the MainFrame
	//----------------------------------------------------------
	public LogoPanel()
	{
		// custom color of the program

		
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ));

		setBackground( View.BANNER_COLOR );

		setBorder (BorderFactory.createEmptyBorder( 5, 0, 0, 0 ));

		// This is top panel of the Logo Panel
		JPanel panel = new JPanel();
		panel.setBackground( View.BANNER_COLOR );
		panel.setBorder(BorderFactory.createEmptyBorder( 0, 0, 5, 0 ));
		panel.setLayout( new BoxLayout( panel, BoxLayout.X_AXIS ));

		// Create the Upper Title of the program with the Boy Scout Troop image
		JLabel iconLabel = new JLabel("",new ImageIcon ( "EOP.png" ),SwingConstants.LEFT);
		JLabel titleLabel = new JLabel( "        EOP Library System        ",
                					  SwingConstants.CENTER );
		titleLabel.setForeground(new Color(0x00553D));

		// Format the font of the Upper Title
		titleLabel.setFont( new Font(View.FONT_NAME, Font.BOLD, 16 ) );

		// install the label into the title panel
		panel.add(iconLabel);
		panel.add( titleLabel );
		JPanel separator = new JPanel();
		
		separator.setBackground( View.SEPARATOR_COLOR );

		add ( panel );

		add( separator );
	}

}

