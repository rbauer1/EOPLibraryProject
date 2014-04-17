package userinterface.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Worker;

public class WorkerTableModel extends AbstractTableModel implements TableModel {
	
	private static final long serialVersionUID = 4213802193658338074L;
	
	private List<Properties> state;
	
	/**	Names of columns as will appear on table in GUI */
	private static final String[] COL_NAMES_DISP = {"Banner ID","First Name","Last Name","Phone","Email","Credentials","Date Last Updated"};
	
	/**	Names of properties corresponding to database  */
	private static final String[] COL_NAMES_PROPS = {"BannerID","FirstName","LastName","ContactPhone","Email","Credentials","DateOfLastUpdate"};
	
	private static final int NUM_COLS = COL_NAMES_DISP.length;

	public WorkerTableModel(List<Worker> workerData) {
		state = new ArrayList<Properties>();
		for(Worker w : workerData) state.add(w.getPersistentState());
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
