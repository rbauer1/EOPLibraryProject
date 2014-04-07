package userinterface.component;

import java.awt.Font;

import javax.swing.JTextArea;

import userinterface.view.form.Form;

/**
 * Text Area to be put in a Form
 */
public class TextArea extends JTextArea implements FormField {

	private static final long serialVersionUID = -2593306789334165111L;
	
	/** Font of a Field **/
	private static final Font FONT = Form.NORMAL_FONT;

	public TextArea() {
		super();
		setFont(FONT);

	}
	
	public TextArea(int rows, int columns) {
		super(rows, columns);
		setFont(FONT);
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
