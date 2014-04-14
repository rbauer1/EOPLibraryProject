package userinterface.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import model.Rental;

public class RentalTableModel extends AbstractTableModel implements TableModel {
	private static final long serialVersionUID = -7783247333000227260L;

	private List<Properties> state;
	
	/**	Names of columns as will appear on table in GUI */
	private static final String[] COL_NAMES_DISP = {"Banner ID","Barcode","Checkout Date","ID of checkout worker"};
	
	/**	Names of properties corresponding to database  */
	private static final String[] COL_NAMES_PROPS = {"BorrowerID","BookID","CheckoutDate", "CheckoutWorkerID"};
	
	private static final int NUM_COLS = COL_NAMES_DISP.length;

	public RentalTableModel(List<Rental> rentalData) {
		state = new ArrayList<Properties>();
		for(Rental r : rentalData) state.add(r.getPersistentState());
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
