package utilities;

public class Key {
	public static final String RESET_MENU = "ResetMenu";

	public static final String ADD_BOOK_TO_LIST = "AddBookToList";
	public static final String REMOVE_BOOK_FROM_LIST = "RemoveBookFromList";
	public static final String BACK = "Back";
	public static final String BOOK = "Book";
	public static final String BOOK_COLLECTION = "BookCollection";
	public static final String OUTSTANDING_BOOKS = "OustandingBooks";


	public static final String BORROWER = "Borrower";
	public static final String BORROWER_COLLECTION = "BorrowerCollection";
	public static final String RENT_BOOKS = "RentBooks";
    public static final String CHECKOUT_BOOKS = "CheckOutBooks";

    public static final String DELINQUENT_BORROWERS_COLLECTION = "DelinquentBorrowersCollection";

	public static final String DISPLAY_BOOK_MENU = "DisplayBookMenu";


	public static final String DISPLAY_BORROWER_MENU = "DisplayBorrowerMenu";
	public static final String DISPLAY_LOGIN = "DisplayLogin";
	/* Menus  */
	public static final String DISPLAY_MAIN_MENU = "DisplayMainMenu";
	public static final String DISPLAY_WORKER_MENU = "DisplayWorkerMenu";

	/* Book Transactions */
	public static final String EXECUTE_ADD_BOOK 	= "AddBookTransaction";
	public static final String EXECUTE_LIST_RENTED_BOOKS 	= "ListRentedBooksTransaction";
	public static final String EXECUTE_LIST_AVAILABLE_BOOKS	= "ListBooksTransaction";
	/* Borrower Transactions */
	public static final String EXECUTE_ADD_BORROWER 	= "AddBorrowerTransaction";
	/* Worker Transactions */
	public static final String EXECUTE_ADD_WORKER 		= "AddWorkerTransaction";
	public static final String EXECUTE_RENT_BOOK = "RentBooksTransaction";
	public static final String EXECUTE_RETURN_BOOK = "ReturnBooksTransaction";
	public static final String EXECUTE_DELETE_BOOK 	= "DeleteBooksTransaction";

	public static final String EXECUTE_DELETE_BORROWER 	= "DeleteBorrowersTransaction";
	public static final String EXECUTE_DELETE_WORKER 	= "DeleteWorkersTransaction";
	public static final String EXECUTE_DELINQUENCY_CHECK 	= "DelinquencyCheckTransaction";
	public static final String EXECUTE_MODIFY_BOOK 	= "ModifyBooksTransaction";
	public static final String EXECUTE_LIST_BORROWERS_WITH_RENTED_BOOKS = "ListBorrowersWithRentedBooksTransaction";

	public static final String EXECUTE_MODIFY_BORROWER 	= "ModifyBorrowersTransaction";
	public static final String EXECUTE_MODIFY_WORKER 	= "ModifyWorkersTransaction";
	public static final String EXECUTE_PROCESS_LOST_BOOK = "ProcessLostBookTransaction";
	public static final String EXECUTE_RECOVER_PASSWORD 		= "RecoverPasswordTransaction";

	public static final String EXIT_SYSTEM = "ExitSystem";
	public static final String GET_PERSISTENT_STATE = "GetPersistentState";

	public static final String INPUT_ERROR = "InputError";
	public static final String INPUT_ERROR_MESSAGES = "InputErrorMessages";
	public static final String LOGIN = "Login";
	public static final String LOGOUT = "Logout";
	public static final String MESSAGE = "Message";



	public static final String OPERATION_TYPE = "OperationType";
	public static final String PW = "Password";
	public static final String RECOVER_PW_COMPLETED = "RecoverPasswordTransactionCompleted";
	public static final String REFRESH_LIST = "RefreshList";
	public static final String RELOAD_ENTITY = "ReloadEntity";

	public static final String RENTAL = "Rental";
	public static final String RENTAL_COLLECTION = "RentalCollection";

	public static final String RESET_PASSWORD = "ResetPassword";
	public static final String SAVE_BOOK = "SaveBook";
	public static final String SAVE_BORROWER = "SaveBorrower";
	public static final String SAVE_ERROR = "SaveError";

	public static final String SAVE_SUCCESS = "SaveSuccess";

	public static final String SAVE_WORKER = "SaveWorker";
	public static final String SELECT_BOOK = "SelectBook";
	public static final String SELECT_BORROWER = "SelectBorrower";
	public static final String SELECT_RENTAL = "SelectRental";

	public static final String SELECT_WORKER = "SelectWorker";

	public static final String SEND_RESET_CODE = "RequestResetToken";
	public static final String TRANSACTION_COMPLETED = "TransactionCompleted";

	public static final String WORKER = "Worker";
	public static final String WORKER_COLLECTION = "WorkerCollection";
	public static final String LOGGED_IN_WORKER = "WorkerCurrentlyLoggedInAs";


	public static final String RETURN_BOOKS = "ReturnBooks";
	public static final String OUTSTANDING_RENTALS = "OutstandingRentals";
	public static final String RETURN_RENTALS = "ReturnRentals";
	public static final String ADD_RENTAL_TO_LIST = "AddRental";
	public static final String REMOVE_RENTAL_FROM_LIST = "RemoveRental";

	public static final String FILTER = "Filter";


	public static final String EXECUTE_PRINT_PDF = "PrintPDFTransaction";
	public static final String PRINTERS = "Printers";
	public static final String PRINT_PREVIEW = "PrintPreview";
	public static final String PRINT_DOCUMENT = "PrintDocument";
	public static final String PRINT = "Print";


	public static final String EXPORT_TO_EXCEL = "ExportToExcel";

	public static final String BOOK_DUE_DATE = "BookDueDate";

	private Key(){};
}
