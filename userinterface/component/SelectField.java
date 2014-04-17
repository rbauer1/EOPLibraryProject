package userinterface.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.border.LineBorder;

import userinterface.view.form.Form;

/**
 * Select Field/ Combo box for use in Form
 */
@SuppressWarnings("rawtypes")
public class SelectField extends JComboBox implements FormField {

	private static final long serialVersionUID = 5022158642069326654L;
	
	/** Size of a Select Box **/
	private static final Dimension SIZE = new Dimension(105, 25);
	
	/** Border Color of a Select Box **/
	private static final Color BORDER_COLOR = Form.FIELD_BORDER_COLOR;
	
	/** Font of a Select Box **/
	private static final Font FONT = Form.NORMAL_FONT;
	
	@SuppressWarnings("unchecked")
	public SelectField(String[] options) {
		super(options);
		setPreferredSize(SIZE);
		setBorder(new LineBorder(BORDER_COLOR, 1));
		setFont(FONT);
		setAlignmentX(CENTER_ALIGNMENT);
	}

	@Override
	public String getValue() {
		return (String) getSelectedItem();
	}

	@Override
	public void setValue(String value) {
		setSelectedItem(value);
	}
	
	@Override
	public void reset() {
		if(getItemCount() == 0){
			return;
		}
		setSelectedIndex(0);
	}
}
