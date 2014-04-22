package userinterface.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Borrower;

public class BorrowerTableModel extends AbstractTableModel implements TableModel {

	private static final long serialVersionUID = 4213802193658338074L;

	private final List<Properties> state;

	/**	Names of columns as will appear on table in GUI */
	private static final String[] COL_NAMES_DISP = {"Banner ID","First Name","Last Name","Phone","Email","Status","Balance","Date Last Updated"};

	/**	Names of properties corresponding to database  */
	private static final String[] COL_NAMES_PROPS = {"BannerID","FirstName","LastName","ContactPhone","Email","BorrowerStatus","MonetaryPenalty","DateOfLastUpdate"};

	private static final int NUM_COLS = COL_NAMES_DISP.length;

	public BorrowerTableModel(List<Borrower> bookData) {
		state = new ArrayList<Properties>();
		for(Borrower b : bookData) {
			state.add(b.getPersistentState());
		}
	}

	@Override
	public int getColumnCount() {
		return NUM_COLS;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if(columnIndex > NUM_COLS || columnIndex < 0) {
			return "??";
		}
		return COL_NAMES_DISP[columnIndex];
	}

	@Override
	public int getRowCount() {
		return state.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Properties reportRow = state.get(rowIndex);

		if (columnIndex >= 0 && columnIndex < NUM_COLS) {
			return reportRow.getProperty(COL_NAMES_PROPS[columnIndex]);
		}

		return null;

	}
}
