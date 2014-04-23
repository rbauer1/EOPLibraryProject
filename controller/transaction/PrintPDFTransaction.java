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

import org.icepdf.ri.common.PrintHelper;
import org.icepdf.ri.common.SwingController;

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
		printerController.openDocument("test.pdf");

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
			System.out.println(printers);
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

		printer.setupPrintService(service, 0, 0, 1, true);
		try {
			printer.print();
		} catch (PrintException e) {
			e.printStackTrace();
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
