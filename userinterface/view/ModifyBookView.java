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

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Book;
import userinterface.ViewHelper;
import userinterface.component.Button;
import userinterface.component.Panel;
import userinterface.view.form.BookForm;
import utilities.Key;
import controller.Controller;

/**
 * View that provides interface for user to add new book.
 */
public class ModifyBookView extends View {
	
	private static final long serialVersionUID = 3340451129170570186L;

	/** Form to take in book data */
	private BookForm form;
	
	private Book book;
	
	/* Buttons */
	private JButton submitButton;
	private JButton resetButton;
	private JButton backButton;
	
	/**
	 * Constructs add book view
	 * @param controller
	 */
	public ModifyBookView(Controller controller) {
		super(controller, "Modify Book");
		
		form = new BookForm(this);
		add(form);
		
		book = (Book) controller.getState(Key.SELECT_BOOK);
		form.setValues(book.getPersistentState());
		
		add(createButtonsPanel());
		
		controller.subscribe(Key.INPUT_ERROR, this);
		controller.subscribe(Key.BOOK_SUBMIT_SUCCESS, this);
	}

	@Override
	public void processAction(EventObject evt) {
		messagePanel.clear();

		if (evt.getSource() == backButton) {
			controller.stateChangeRequest(Key.DISPLAY_BOOK_MENU, null);
		}else if (evt.getSource() == resetButton){
			form.reset();
		}else if (evt.getSource() == submitButton) {
			controller.stateChangeRequest(Key.SUBMIT_BOOK, form.getValues());
		}
	}

	@Override
	public void updateState(String key, Object value) {
		if (key.equals(Key.INPUT_ERROR)) {
			messagePanel.displayErrorMessage("There was an error");
		}else if(key.equals(Key.BOOK_SUBMIT_SUCCESS)){
			messagePanel.displayMessage("Success", "Book saved successfully"); 
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

		buttonPanel.add(new JLabel("     "));
		
		resetButton = new Button("Reset");
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);

		buttonPanel.add(new JLabel("     "));

		backButton = new Button("Back");
		backButton.addActionListener(this);
		buttonPanel.add(backButton);

		return ViewHelper.formatCenter(buttonPanel);
	}
}