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

import java.awt.FlowLayout;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Worker;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import userinterface.component.TextField;
import userinterface.view.form.Form;
import userinterface.view.form.WorkerForm;
import utilities.Key;
import controller.Controller;
/**
 * View that provides interface for user to modify a worker.
 */
public class ModifyWorkerView extends View {
	
	private static final long serialVersionUID = 3340451129170570186L;

	/** Form to take in worker data */
	private Form form;
	
	private Worker worker;
	
	/* Buttons */
	private JButton submitButton;
	private JButton resetButton;
	private JButton backButton;
	
	/**
	 * Constructs modify book view
	 * @param controller
	 */
	public ModifyWorkerView(Controller controller) {
		super(controller, "Modify Worker");
		
		form = new WorkerForm(this);
		add(form);
		
		worker = (Worker) controller.getState(Key.SELECT_WORKER);
		form.setValues(worker.getPersistentState());
		
		//TODO implement this cleaner
		((TextField)form.get("BannerID")).setEnabled(false);
		
		add(createButtonsPanel());
		
		controller.subscribe(Key.INPUT_ERROR, this);
		controller.subscribe(Key.SAVE_SUCCESS, this);
		controller.subscribe(Key.SAVE_ERROR, this);
	}

	@Override
	public void processAction(EventObject evt) {
		messagePanel.clear();

		if (evt.getSource() == backButton) {
			controller.stateChangeRequest(Key.DISPLAY_WORKER_MENU, null);
		}else if (evt.getSource() == resetButton){
			form.reset();
		}else if (evt.getSource() == submitButton) {
			controller.stateChangeRequest(Key.SUBMIT_WORKER, form.getValues());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			System.out.println((List<String>) value);
			messagePanel.displayErrorMessage("Aw shucks! There are errors in the input. Please try again.", (List<String>) value);
		}else if(key.equals(Key.SAVE_SUCCESS)){
			messagePanel.displayMessage("Success", "Well done! Worker was sucessfully saved."); 
		}else if(key.equals(Key.SAVE_ERROR)){
			messagePanel.displayErrorMessage("Whoops! An error occurred while saving.");
		}
	}
	
	/**
	 * Create button panel for this view.
	 * @return button panel
	 */
	private JPanel createButtonsPanel() {
		JPanel buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER));

		submitButton = new Button("Save");
		submitButton.addActionListener(this);
		buttonPanel.add(submitButton);

//		buttonPanel.add(new JLabel("     "));
//		
//		resetButton = new Button("Reset");
//		resetButton.addActionListener(this);
//		buttonPanel.add(resetButton);

		buttonPanel.add(new JLabel("     "));

		backButton = new Button("Back");
		backButton.addActionListener(this);
		buttonPanel.add(backButton);

		return ViewHelper.formatCenter(buttonPanel);
	}
}