package document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.table.TableModel;

import userinterface.MainFrame;
import utilities.DateUtil;
import controller.Controller;

public class ExcelDocument {

	protected StringBuilder document;

	protected Controller controller;

	protected ExcelDocument (Controller controller) {
		this.controller = controller;
		document = new StringBuilder();
	}

	protected void createTable(TableModel tableModel){
		int numColumns = tableModel.getColumnCount();
		int numRows = tableModel.getRowCount();

		for(int col = 0; col < numColumns; col++){
			document.append("\"" + tableModel.getColumnName(col) + "\"");
			if(col != numColumns - 1){
				document.append(',');
			}
		}
		document.append('\n');


		for(int row = 0; row < numRows; row++){
			for(int col = 0; col < numColumns; col++){
				document.append("\"" + tableModel.getValueAt(row, col) + "\"");
				if(col != numColumns - 1){
					document.append(',');
				}
			}
			document.append('\n');
		}
	}

	protected void createTimestamp(){
		document.append("Created On: " + DateUtil.getDateTime() + "\n");
	}

	protected void createTitle(String title){
		document.append(title + '\n');
	}

	public boolean save(){
		final JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
		if(fileChooser.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
			return save(fileChooser.getSelectedFile());
		}
		return false;
	}

	public boolean save(File file){
		try {

			if(!file.getName().toLowerCase().endsWith(".csv")){
				return save(file.getCanonicalPath() + ".csv");
			}

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fileWriter);
			bw.write(document.toString());
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean save(String filePath){
		return save(new File(filePath));
	}

}
