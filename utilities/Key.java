package utilities;

public class Key {
	public static final String EXIT_SYSTEM = "ExitSystem";
	public static final String LOGOUT = "Logout";
	public static final String LOGIN = "Login";
	
	public static final String LOGIN_ERROR = "LoginError";
	
	public static final String SAVE_SUCCESS = "SaveSuccess";
	public static final String SAVE_ERROR = "SaveError";
	public static final String INPUT_ERROR = "InputError";
	
	public static final String GET_PERSISTENT_STATE = "GetPersistentState";
	
	public static final String SUBMIT_BOOK = "SubmitNewOrModifiedBook";
	public static final String SUBMIT_BORROWER = "SubmitNewOrModifiedBorrower";
	public static final String SUBMIT_WORKER = "SubmitNewOrModifiedWorker";
	public static final String SELECT_BOOK = "SelectBook";
	public static final String SELECT_BORROWER = "SelectBorrower";
	public static final String MODIFY_OR_DELETE = "ModifyOrDelete";
	
	public static final String SUBMIT_NEW_WORKER = "SubmitNewWorker";
	
	public static final String DISPLAY_MAIN_MENU = "DisplayMainMenu";
	public static final String DISPLAY_BOOK_MENU = "DisplayBookMenu";
	public static final String DISPLAY_WORKER_MENU = "DisplayWorkerMenu";
	public static final String DISPLAY_BORROWER_MENU = "DisplayBorrowerMenu";
	
	public static final String GET_BOOK_COLLECTION = "GetBookCollection";
	public static final String GET_BORROWER_COLLECTION = "GetBorrowerCollection";
	
	public static final String BORROWER_SUBMIT_SUCCESS = "BorrowerAddedOrModifiedSuccessfully";
	public static final String WORKER_SUBMIT_SUCCESS = "WorkerAddedOrModifiedSuccessfully";
	
	/* Book Transactions */
	public static final String EXECUTE_ADD_BOOK 	= "AddBookTransaction";
	public static final String EXECUTE_MODIFY_BOOK 	= "ModifyBooksTransaction";
	public static final String EXECUTE_DELETE_BOOK 	= "DeleteBooksTransaction";
	
	/* Borrower Transactions */
	public static final String EXECUTE_ADD_BORROWER 	= "AddBorrowerTransaction";
	public static final String EXECUTE_MODIFY_BORROWER 	= "ModifyBorrowersTransaction";
	public static final String EXECUTE_DELETE_BORROWER 	= "DeleteBorrowersTransaction";
	
	/* Worker Transactions */
	public static final String EXECUTE_ADD_WORKER 		= "AddWorkerTransaction";
	public static final String EXECUTE_MODIFY_WORKER 	= "ModifyWorkersTransaction";
	public static final String EXECUTE_DELETE_WORKER 	= "DeleteWorkersTransaction";
	public static final String EXECUTE_RECOVER_PW 		= "RecoverPasswordTransaction";
	
	public static final String RECOVER_PW_COMPLETED = "RecoverPasswordTransactionCompleted";
	
	public static final String TRANSACTION_COMPLETED = "TransactionCompleted";
	
	public static final String REQUEST_RESET_TOKEN = "RequestResetToken";
	
	public static final String RESET_PW = "ResetPassword";
	public static final String PW = "Password";
	
	public static final String OPERATION_TYPE = "OperationType";
	public static final String REFRESH_LIST = "RefreshList";
	
	
	private Key(){};
}
