package userinterface;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CSVFileFilter extends FileFilter {

	// -------------------------------------------
	public boolean accept(File f) {
		String fileName = f.getName();

		if ((fileName.endsWith(".csv")) || (fileName.endsWith(".CSV"))
				|| (f.isDirectory()))
			return true;

		else
			return false;
	}

	// -------------------------------------------
	public String getDescription() {
		return "CSV File Filter";
	}
}
