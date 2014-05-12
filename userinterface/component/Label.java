package userinterface.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import userinterface.view.form.Form;

/**
 * Styled label class
 */
public class Label extends JLabel {

	private static final long serialVersionUID = -3959013830304850317L;
	
	/** Font used for general items */
	private static final Font NORMAL_FONT = Form.NORMAL_FONT;
	
	/** Font used for headings */
	private static final Font HEADING_FONT = new Font("Arial", Font.BOLD, 18);
	
	/** Color of heading */
	private static final Color HEADING_COLOR = new Color(0x668D3C).darker();
	
	/** Size of Label */
	public static final Dimension SIZE_LABEL = new Dimension(150, 30);
	
	/**
	 * Constructs a standard label.
	 * @param text
	 */
	public Label(String text) {
		this(text, false);
	}
	
	/**
	 * Constructs a label of provided style.
	 * @param text
	 * @param heading - if true uses heading styles
	 */
	public Label(String text, boolean heading) {
		super(text);
		setAlignmentX(CENTER_ALIGNMENT);
		if(heading){
			setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
			setFont(HEADING_FONT);
			setForeground(HEADING_COLOR);
		}else{
			setFont(NORMAL_FONT);
			setPreferredSize(SIZE_LABEL);
		}
	}
	
	/**
	 * @param text
	 * @param alignmentX - Component Alignment for X-axis
	 */
	public Label(String text, float alignmentX) {
		super(text);
		setAlignmentX(alignmentX);
		setFont(NORMAL_FONT);
	}
	
	/**
	 * @param text
	 * @param alignmentX - Component Alignment for X-axis
	 * @param heading - if true uses heading styles
	 */
	public Label(String text, float alignmentX, boolean heading) {
		super(text);
		setAlignmentX(alignmentX);
		if(heading){
			setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
			setFont(HEADING_FONT);
			setForeground(HEADING_COLOR);
		}else{
			setFont(NORMAL_FONT);
			setPreferredSize(SIZE_LABEL);
		}
	}

}
