/**
 * COPYRIGHT 2014 Sandeep Mitra and students
 * The College at Brockport, State University of New York.
 * ALL RIGHTS RESERVED
 * 
 * This file is the product of The College at Brockport and cannot
 * be reproduced, copied, or used in any shape or form without
 * he express written consent of The College at Brockport. *
 */
package userinterface.view;

import java.awt.Container;
import java.awt.Dimension;
import java.util.Properties;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import userinterface.ViewHelper;
import userinterface.message.MessageEvent;
import userinterface.view.form.Form;
import userinterface.view.form.PrintForm;
import utilities.Key;
import controller.Controller;

public class PrintPDFView extends View {

	private static final long serialVersionUID = 7175169568245164906L;

	private static final String[] BUTTON_NAMES = { "Print", "Done" };

	/** allows input of settings for printing */
	private Form form;

	public PrintPDFView(Controller controller) {
		super(controller, "Print Preview", BUTTON_NAMES);
	}

	@Override
	protected void build() {
		form = new PrintForm(this);
		Properties formDefaults = new Properties();
		formDefaults.setProperty("Copies", "1");
		PrintService defaultPrinterService = PrintServiceLookup.lookupDefaultPrintService();
		if(defaultPrinterService != null) {
			formDefaults.setProperty("Printer", defaultPrinterService.getName());
		}
		form.setValues(formDefaults);
		add(ViewHelper.formatCenter(form, 5));
		Container previewContainer = (Container)controller.getState(Key.PRINT_PREVIEW);
		previewContainer.setMaximumSize(new Dimension(600, 400));
		add(previewContainer);
	}

	public String[] getPrinters() {
		return (String[])controller.getState(Key.PRINTERS);
	}

	@Override
	public void processAction(Object source) {
		if (source == buttons.get("Print")) {
			controller.stateChangeRequest(Key.PRINT, form.getValues());
		} else if (source == buttons.get("Done")) {
			controller.stateChangeRequest(Key.DISPLAY_MAIN_MENU, null);
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.MESSAGE)) {
			messagePanel.displayMessage((MessageEvent)value);
		}
	}
}
