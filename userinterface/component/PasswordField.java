package userinterface.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPasswordField;
import javax.swing.border.LineBorder;

import userinterface.view.form.Form;

/**
 * Password Field to be put in a Form.
 */
public class PasswordField extends JPasswordField implements FormField {

	private static final long serialVersionUID = -8018759490264572644L;

	/** Border Color of a Field **/
	private static final Color BORDER_COLOR = Form.FIELD_BORDER_COLOR;
	
	/** Font of a Field **/
	private static final Font FONT = Form.NORMAL_FONT;
	
	/** Size of Field */
	private static final Dimension SIZE = new Dimension(185, 25);
	
	public PasswordField(int columns) {
		super(columns);
		setFont(FONT);
		setAlignmentX(CENTER_ALIGNMENT);
		setBorder(new LineBorder(BORDER_COLOR, 1));
		setPreferredSize(SIZE);
	}

	@Override
	public String getValue() {
		return new String(getPassword());
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
