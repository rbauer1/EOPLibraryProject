/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package controller.transaction;

import java.util.Arrays;
import java.util.Properties;

import javax.print.DocFlavor;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;

import org.icepdf.ri.common.PrintHelper;
import org.icepdf.ri.common.SwingController;

import userinterface.MainFrame;
import userinterface.message.MessageEvent;
import userinterface.message.MessageType;
import utilities.Key;
import controller.Controller;

/**
 * Transaction that handles the process of printing a pdf
 */
public class PrintPDFTransaction extends Transaction {

	private SwingController printerController;
	private PrintService[] printServices;
	private String[] printers;

	/**
	 * Constructs a Print PDF Transaction
	 * @param parentController
	 */
	public PrintPDFTransaction(Controller parentController) {
		super(parentController);
	}

	@Override
	public void execute() {
		printerController = new SwingController();
		printerController.setIsEmbeddedComponent(true);
		printerController.openDocument((String)parentController.getState(Key.PRINT_DOCUMENT));
		printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE, null);
		printers = new String[printServices.length];

		for (int i = 0; i < printServices.length; i++) {
			printers[i] = printServices[i].getName();
		}

		showView("PrintPDFView");
	}

	@Override
	public Object getState(String key) {
		if(key.equals(Key.PRINTERS)) {
			return printers;
		}
		if(key.equals(Key.PRINT_PREVIEW)) {
			return printerController.getDocumentViewController().getViewContainer();
		}
		return super.getState(key);
	}

	private void print(Properties printSettings) {
		PrintHelper printer = new PrintHelper(printerController);

		PrintService service = printServices[Arrays.asList(printers).indexOf(printSettings.getProperty("Printer"))];
		int numCopies = Integer.parseInt(printSettings.getProperty("Copies", "0"));
		printer.setupPrintService(service, 0, printerController.getDocument().getNumberOfPages(), numCopies, true);
		try {
			printer.print();
			JOptionPane.showMessageDialog(MainFrame.getInstance(), "Good Job! Document successfully sent to the printer.");
			stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		} catch (PrintException e) {
			stateChangeRequest(Key.MESSAGE, new MessageEvent(MessageType.ERROR, "Whoops! An error occurred while printing. Please try again."));
		}

	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.PRINT)) {
			print((Properties)value);
		}
		super.stateChangeRequest(key, value);
	}
}
