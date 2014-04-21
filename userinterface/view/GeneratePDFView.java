package userinterface.view;

import controller.Controller;
import org.icepdf.ri.common.PrintHelper;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.views.DocumentViewController;
import org.icepdf.ri.common.views.DocumentViewControllerImpl;
import utilities.Key;

import javax.print.*;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrintQuality;
import javax.swing.*;

/**
 * Created by chaber_e on 14/04/2014.
 * For Package userinterface.view.form
 */
public class GeneratePDFView extends View {
	private static final String[] BUTTON_NAMES = {"Print", "Save", "Back"};

	JComboBox<String> printDevice;
	JComboBox<String> printQuality;
	JTextField  numberOfCopies;

	SwingController swingController;
	PrintService[]  printServices;
	String[]        printServicesString;
	PrintHelper     printer;


	public GeneratePDFView(Controller controller) {
		super(controller, "Generate PDF", BUTTON_NAMES);
	}

	@Override
	public void processAction(Object source) {
		if (source == buttons.get("Print"))
			printAction();
		else if (source == buttons.get("Save"))
			saveAction();
		else if (source == buttons.get("Back"))
			controller.stateChangeRequest(Key.DISPLAY_MAIN_MENU,null);
	}

	protected void   initPrintService()
	{
		printServices = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE,null);

		printServicesString = new String[printServices.length];

		for (int cp = 0; printServices.length > cp; cp++)
			printServicesString[cp] = printServices[cp].getName();
	}

	protected JPanel buildActionPanel()
	{
		JPanel     actionPanel = new JPanel();

		actionPanel.add(new JLabel("Printer"));

		initPrintService();
		printDevice = new JComboBox<String>(printServicesString);
		printDevice.addActionListener(this);
		actionPanel.add(printDevice);

		actionPanel.add(new JLabel("Quality"));

		printQuality = new JComboBox<String>(new String[]{"LOW", "MID", "HIGH"});
		printQuality.addActionListener(this);
		actionPanel.add(printQuality);

		actionPanel.add(new JLabel("Copie(s)"));
		numberOfCopies = new JTextField(1);
		numberOfCopies.addActionListener(this);
		actionPanel.add(numberOfCopies);

		return actionPanel;
	}

	@Override
	protected void build() {
		swingController = new SwingController();
		swingController.setIsEmbeddedComponent(true);
		swingController.openDocument("c:/assign3.pdf");

		DocumentViewController viewController = swingController.getDocumentViewController();

		add(buildActionPanel());
		add(viewController.getViewContainer());
	}

	private void    printAction()
	{
		PrintService[] services = PrintServiceLookup.lookupPrintServices(DocFlavor.SERVICE_FORMATTED.PAGEABLE,null);

		printer = new PrintHelper(swingController);

		printer.setupPrintService(services[printDevice.getSelectedIndex()],0,0,1,true);
		try {
			printer.print();
		} catch (PrintException e) {
			e.printStackTrace();
		}
	}

	private void    saveAction()
	{

	}

	@Override
	public void updateState(String key, Object value) {

	}
}
