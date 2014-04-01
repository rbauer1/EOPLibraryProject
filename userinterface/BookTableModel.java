package userinterface;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

//==============================================================================
public class BookTableModel extends AbstractTableModel implements TableModel
{
	private static final long serialVersionUID = 4213802193658338074L;
	private ArrayList myState;
	private ArrayList myColumns;

	public BookTableModel ( ArrayList treeData )
	{
		myState = treeData;


		//--------------------------------------------------------
		myColumns = new ArrayList();

		if ( myState.size() > 0 )
		{
			Properties firstElement = ( Properties )myState.get(0);

			Enumeration allColumnNames = firstElement.keys();

			while ( allColumnNames.hasMoreElements() )
			{
				String nextColumnName = (String)allColumnNames.nextElement();
				myColumns.add( nextColumnName );
			}

		}

		//----------------------------------------------------------------


	}

	//--------------------------------------------------------------------------
	public int getColumnCount()
	{
		//return 5;
		return myColumns.size();
	}

	//--------------------------------------------------------------------------
	public int getRowCount()
	{
		return myState.size();
	}

	//--------------------------------------------------------------------------
	public Object getValueAt ( int rowIndex, int columnIndex )
	{		
		//-----------------------------------------------------------------
		Properties reportRow = (Properties) myState.get( rowIndex );

		if (( columnIndex >= 0 ) && ( columnIndex <= myColumns.size() - 1 ))
		{
			String columnName = ( String )myColumns.get( columnIndex );
			return reportRow.getProperty( columnName );
		}

		return null;
		//-----------------------------------------------------------------


	}

	//--------------------------------------------------------------------------
	public String getColumnName( int columnIndex )
	{
		//----------------------------------------------------------
		if (( columnIndex >= 0 ) && ( columnIndex <= myColumns.size() - 1 ))
		{
			return ( String ) myColumns.get( columnIndex );
		}

		return "UNKNOWN";
		//------------------------------------------------------------------
	}
}
