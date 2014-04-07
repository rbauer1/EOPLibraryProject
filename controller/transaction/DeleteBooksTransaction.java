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


import javax.swing.JOptionPane;

import model.Book;
import utilities.Key;
import controller.Controller;

public class DeleteBooksTransaction extends Transaction {
	
	private Transaction listBooksTransaction;

	/**
	 * Constructs Delete Books Transaction
	 * @param parentController
	 */
	public DeleteBooksTransaction(Controller parentController) {
		super(parentController);
	}
	
	@Override
	public Object getState(String key) {
		return null;
	}
	
	@Override
	public void execute(){
		listBooksTransaction = TransactionFactory.executeTransaction(this, "ListBooksTransaction", Key.DISPLAY_BOOK_MENU, Key.SELECT_BOOK);
	}

	@Override
	public void stateChangeRequest(String key, Object value) {
		if(key.equals(Key.SELECT_BOOK)){
			setBookInactive((Book)value);
		}
		registry.updateSubscribers(key, this);
	}
	
	private void setBookInactive(Book book){
		if(deleteConfirmationPopup() == JOptionPane.YES_OPTION){
			book.setInactive();
			listBooksTransaction.stateChangeRequest(Key.REFRESH_LIST, null);
		}
	}
	
	private int deleteConfirmationPopup(){
		String message = "ATTENTION: You are about to delete a book from the system.\n" +
			"Are you sure you have selected the correct book and want to proceed?";
		return JOptionPane.showConfirmDialog(frame, message, "Book will be deleted", JOptionPane.YES_NO_OPTION);

	}
}
