package userinterface;

// System imports
import java.awt.Toolkit;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

//==============================================================
public class CurrencyTextField extends JTextField
{	
	private static final long serialVersionUID = -5392670994300492212L;
	private int limit;
	protected int decimalPrecision = 2;

	//----------------------------------------------------------
	public CurrencyTextField(int columns, int limit)
	{
		super();
		setColumns(columns);
		this.limit = limit;
	}

	//----------------------------------------------------------
    protected Document createDefaultModel()
    {
        return new NumericDocument();
    }

    //----------------------------------------------------------
    private class NumericDocument extends PlainDocument
    {
		private static final long serialVersionUID = -2707715508478460537L;
		// '\d' is the regex for digit (\\d* means zero or more digits)
        private final Pattern DIGITS = Pattern.compile("\\d*");
        
    	//----------------------------------------------------------
        public void insertString(int offset, String str, AttributeSet attr) 
        	throws BadLocationException 
        {
             if(str != null && (getLength() + str.length()) <= limit)
             {
            	 // First, check if the value is pre-populated
            	 if(str.length() > 1 && str.contains("."))
            	 {
            		 try
            		 {
            			 Double.parseDouble(str);
            		 }
            		 catch(NumberFormatException ex)
            		 {
            			 Toolkit.getDefaultToolkit().beep();
                         return;
            		 }
            	 }
            	 else
            	 // Then, check if the character is valid
            	 if(!DIGITS.matcher(str).matches() && !str.equals("."))
                 {  
            		 Toolkit.getDefaultToolkit().beep();
                     return;
                 }
            	 else
                 // Now, check if we can put the dot here
            	 if(str.equals(".") && super.getLength() == 0)
                 {
                	 Toolkit.getDefaultToolkit().beep();
                     return;
                 }
            	 else
                 // Now, check if dot is in the end
            	 if(str.equals(".") && super.getLength() == limit - 1)
                 {
                	 Toolkit.getDefaultToolkit().beep();
                     return;
                 }
                 else
                 // Now, check if we can put a decimal here
                 if(str.equals(".") && super.getText(0, super.getLength()).contains("."))
                 { 
                       Toolkit.getDefaultToolkit().beep();
                       return;
                 }
                 else 
                 // Now, check if we less then or equal to the decimal precision limit	
                 if(DIGITS.matcher(str).matches() && 
                    super.getText(0, super.getLength()).indexOf(".") != -1 && 
                    offset > super.getText(0, super.getLength()).indexOf(".") && 
                    super.getLength() - super.getText(0, super.getLength()).indexOf(".") > decimalPrecision)
                 {  
                	 Toolkit.getDefaultToolkit().beep();
                	 return;
                 }
                  
            	 // Finally, if we pass all the tests, we are good to go
                 super.insertString(offset, str, attr);
             }
             else
             {
            	 Toolkit.getDefaultToolkit().beep();
                 return;
             }
             
             return;
        }
    }
}