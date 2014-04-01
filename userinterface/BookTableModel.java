package userinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Book;

//==============================================================================
public class BookTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = 4213802193658338074L;
	private List<Properties> state;
	/**	Names of columns as will appear on table in GUI */
	private static final String[] COL_NAMES_DISP = {"Barcode","Title","Author","Discipline","ISBN","Date Of Last Update"};
	/**	Names of properties corresponding to database  */
	private static final String[] COL_NAMES_PROPS = {"Barcode","Title","Author1","Discipline","ISBN","DateOfLastUpdate"};
	private static final int NUM_COLS = 6;

	public BookTableModel(List<Book> bookData) {
		state = new ArrayList<Properties>();
		for(Book b : bookData) state.add(b.getPersistentState());
	}

	public int getColumnCount() {
		return NUM_COLS;
	}

	public int getRowCount() {
		return state.size();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Properties reportRow = state.get(rowIndex);

		if (columnIndex >= 0 && columnIndex < NUM_COLS) {
			return reportRow.getProperty(COL_NAMES_PROPS[columnIndex]);
		}

		return null;

	}

	public String getColumnName(int columnIndex) {
		if(columnIndex > NUM_COLS || columnIndex < 0) return "??";
		return COL_NAMES_DISP[columnIndex];
	}
}
