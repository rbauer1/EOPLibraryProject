package utilities;

public class Key {
	public static final String EXIT_SYSTEM = "ExitSystem";
	public static final String LOGOUT = "Logout";
	public static final String LOGIN = "Login";
		
	public static final String SAVE_SUCCESS = "SaveSuccess";
	public static final String SAVE_ERROR = "SaveError";
	public static final String INPUT_ERROR = "InputError";
	public static final String INPUT_ERROR_MESSAGES = "InputErrorMessages";

	
	public static final String BOOK = "Book";
	public static final String WORKER = "Worker";
	public static final String BORROWER = "Borrower";
	public static final String RENTAL = "Rental";
	
	public static final String BOOK_COLLECTION = "BookCollection";
	public static final String BORROWER_COLLECTION = "BorrowerCollection";
	public static final String WORKER_COLLECTION = "WorkerCollection";
	public static final String RENTAL_COLLECTION = "RentalCollection";
	
	public static final String MESSAGE = "Message";
	
	public static final String SAVE_BOOK = "SaveBook";
	public static final String SAVE_BORROWER = "SaveBorrower";
	public static final String SAVE_WORKER = "SaveWorker";
	
	public static final String SELECT_BOOK = "SelectBook";
	public static final String SELECT_BORROWER = "SelectBorrower";
	public static final String SELECT_WORKER = "SelectWorker";
	public static final String SELECT_RENTAL = "SelectRental";
	
	public static final String RELOAD_ENTITY = "ReloadEntity";
	public static final String GET_PERSISTENT_STATE = "GetPersistentState";		
		
	/* Menus  */
	public static final String DISPLAY_MAIN_MENU = "DisplayMainMenu";
	public static final String DISPLAY_BOOK_MENU = "DisplayBookMenu";
	public static final String DISPLAY_WORKER_MENU = "DisplayWorkerMenu";
	public static final String DISPLAY_BORROWER_MENU = "DisplayBorrowerMenu";
	public static final String DISPLAY_LOGIN = "DisplayLogin";

	

	/* Book Transactions */
	public static final String EXECUTE_ADD_BOOK 	= "AddBookTransaction";
	public static final String EXECUTE_MODIFY_BOOK 	= "ModifyBooksTransaction";
	public static final String EXECUTE_DELETE_BOOK 	= "DeleteBooksTransaction";
	public static final String EXECUTE_PROCESS_LOST_BOOK = "ProcessLostBookTransaction";
	
	/* Borrower Transactions */
	public static final String EXECUTE_ADD_BORROWER 	= "AddBorrowerTransaction";
	public static final String EXECUTE_MODIFY_BORROWER 	= "ModifyBorrowersTransaction";
	public static final String EXECUTE_DELETE_BORROWER 	= "DeleteBorrowersTransaction";
	
	/* Worker Transactions */
	public static final String EXECUTE_ADD_WORKER 		= "AddWorkerTransaction";
	public static final String EXECUTE_MODIFY_WORKER 	= "ModifyWorkersTransaction";
	public static final String EXECUTE_DELETE_WORKER 	= "DeleteWorkersTransaction";
	
	public static final String EXECUTE_RECOVER_PASSWORD 		= "RecoverPasswordTransaction";
	
	
	
	public static final String RECOVER_PW_COMPLETED = "RecoverPasswordTransactionCompleted";
	
	public static final String TRANSACTION_COMPLETED = "TransactionCompleted";
	
	public static final String SEND_RESET_CODE = "RequestResetToken";
	
	public static final String RESET_PASSWORD = "ResetPassword";
	public static final String PW = "Password";
	
	public static final String OPERATION_TYPE = "OperationType";
	public static final String REFRESH_LIST = "RefreshList";
	
	
	
	
	private Key(){};
}
