package userinterface.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import userinterface.view.form.Form;

/**
 * Text Area to be put in a Form
 */
public class TextArea extends JTextArea implements FormField {

	private static final long serialVersionUID = -2593306789334165111L;
	
	/** Border Color of a Field **/
	private static final Color BORDER_COLOR = Form.FIELD_BORDER_COLOR;
	
	/** Font of a Field **/
	private static final Font FONT = Form.NORMAL_FONT;
	
	/** Size of Field */
	private static final Dimension SIZE = new Dimension( 300, 70 );

	public TextArea() {
		super();
		setFont(FONT);
		setAlignmentX(CENTER_ALIGNMENT);
		setBorder(new LineBorder(BORDER_COLOR, 1));
		setPreferredSize(SIZE);
		setMaximumSize(SIZE);
	}
	
	public TextArea(int rows, int columns) {
		super(rows, columns);
		setFont(FONT);
		setAlignmentX(CENTER_ALIGNMENT);
		setBorder(new LineBorder(BORDER_COLOR, 1));
		setPreferredSize(SIZE);
		setMaximumSize(SIZE);
	}

	@Override
	public String getValue() {
		return getText().trim();
	}
	
	@Override
	public void setValue(String value) {
		setText(value);
	}
	
	@Override
	public void reset() {
		setText("");
	}

}
