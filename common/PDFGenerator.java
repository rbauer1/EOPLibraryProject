package common;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import utilities.DateUtil;
import model.Book;
import model.Borrower;
import model.Worker;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by chaber_e on 21/04/2014.
 * For Package common
 */
public class PDFGenerator {
	public final static String LOST_BOOK_ACTION = "generateLostBookPDFContent";
	public final static String RETURN_BOOK_ACTION = "generateReturnPDFContent";
	private final static Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
	private final static Font SUB_TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);


	private static void addContentActors(Document pdf, Borrower borrower, Worker worker) throws DocumentException
	{
		Paragraph   actorsDiv = new Paragraph();
		PdfPTable   transactionActors = new PdfPTable(2);
		Properties  borrowerProperties = borrower.getPersistentState();
		Properties  workerProperties = worker.getPersistentState();

		actorsDiv.add(transactionActors);

		transactionActors.setWidthPercentage(100);
		transactionActors.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		transactionActors.getDefaultCell().setPadding(10);

		PdfPTable borrowerTable = new PdfPTable(1);
		borrowerTable.getDefaultCell().setBorder(0);
		borrowerTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		borrowerTable.addCell(new Paragraph("Borrower", SUB_TITLE_FONT));
		borrowerTable.addCell(new Paragraph(borrowerProperties.getProperty("FirstName")+" "+borrowerProperties.getProperty("LastName")));
		borrowerTable.addCell(new Paragraph(borrowerProperties.getProperty("BannerId")));
		borrowerTable.addCell(new Paragraph(borrowerProperties.getProperty("Email")));
		borrowerTable.addCell(new Paragraph(borrowerProperties.getProperty("ContactPhone")));
		borrowerTable.addCell(new Paragraph(borrowerProperties.getProperty("MonetaryPenalty")+"$"));
		transactionActors.addCell(borrowerTable);

		PdfPTable workerTable = new PdfPTable(1);
		workerTable.getDefaultCell().setBorder(0);
		workerTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		workerTable.addCell(new Paragraph("Worker", SUB_TITLE_FONT));
		workerTable.addCell(new Paragraph(workerProperties.getProperty("FirstName")+" "+workerProperties.getProperty("LastName")));
		workerTable.addCell(new Paragraph(workerProperties.getProperty("BannerID")));
		workerTable.addCell(new Paragraph(workerProperties.getProperty("Email")));
		workerTable.addCell(new Paragraph(workerProperties.getProperty("ContactPhone")));
		transactionActors.addCell(workerTable);
		transactionActors.getRow(0).getCells()[0].setPaddingLeft(0);
		transactionActors.getRow(0).getCells()[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
		transactionActors.getRow(0).getCells()[1].setPaddingRight(0);

		addEmptyLine(actorsDiv, 1);

		pdf.add(actorsDiv);
	}

	private static void addContentLostBook(PdfWriter writer, Document pdf, Book book, Borrower borrower, Worker worker) throws DocumentException
	{
		Paragraph   subTitle = new Paragraph();
		Paragraph   lostBookDiv = new Paragraph();
		PdfPTable   lostBookTable = new PdfPTable(2);
		Properties  bookProperties = book.getPersistentState();

		subTitle.add(new Paragraph("Lost Book Details", SUB_TITLE_FONT));
		subTitle.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(subTitle,1);
		pdf.add(subTitle);

		lostBookDiv.add(lostBookTable);

		lostBookTable.setWidthPercentage(100);
		lostBookTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		lostBookTable.getDefaultCell().setPadding(10);

		lostBookTable.addCell(new Paragraph("Barcode"));
		lostBookTable.addCell(createBarCode39(bookProperties.getProperty("Barcode"), writer.getDirectContent()));
		lostBookTable.getRow(0).getCells()[1].setFixedHeight(100);

		lostBookTable.addCell(new Paragraph("Title"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Title")));

		lostBookTable.addCell(new Paragraph("Discipline"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Discipline")));

		lostBookTable.addCell(new Paragraph("Authors"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Author1")));

		lostBookTable.addCell(new Paragraph("Publisher"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Publisher")));

		lostBookTable.addCell(new Paragraph("ISBN"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("ISBN")));

		lostBookTable.addCell(new Paragraph("Condition"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Condition")));

		lostBookTable.addCell(new Paragraph("Status"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Status")));

		lostBookTable.addCell(new Paragraph("Price"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("SuggestedPrice")));

		lostBookTable.addCell(new Paragraph("Notes"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Notes")));
		
		lostBookTable.addCell(new Paragraph("Date"));
		lostBookTable.addCell(new Paragraph(DateUtil.getDateTime()));

		pdf.add(lostBookDiv);
	}
	
	private static void addContentReturn(PdfWriter writer, Document pdf, ArrayList<Book> returnedBooks, ArrayList<Book> outstandingBooks, Borrower borrower, Worker worker) throws DocumentException
	{
		Paragraph   subTitle = new Paragraph();
		

		subTitle.add(new Paragraph("Returned Book Details", SUB_TITLE_FONT));
		subTitle.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(subTitle,1);
		pdf.add(subTitle);

		
		for(Book book : returnedBooks){
			Paragraph bookParagraph = makeBookTable(writer, book);
			addEmptyLine(bookParagraph,1);
			pdf.add(bookParagraph);
		}
		
		if(outstandingBooks!= null && !outstandingBooks.isEmpty()){
			subTitle = new Paragraph();
			subTitle.add(new Paragraph("Outstanding Book Details", SUB_TITLE_FONT));
			subTitle.setAlignment(Element.ALIGN_LEFT);
			addEmptyLine(subTitle,1);
			pdf.add(subTitle);
			Paragraph outstandingNotice = new Paragraph();
			outstandingNotice
					.add("Our records indicated that you took advantage of the EOP Book Loan Program and were issued: "
							+ outstandingBooks.size() + " book(s) that have yet to be returned. You are required to return each of "
							+ "these books prior to the end of the semester. Each book must be returned to "
							+ "us or accounted for by ****Date to be plugged in manually****. Your ability to rent books will be "
							+ "held until these books have been accounted for. There could possibly be a hold placed on your student "
							+ "account at Brockport if you are not returning, which will prevent you from registering or "
							+ "acquiring transcripts.");
			addEmptyLine(outstandingNotice,1);
			pdf.add(outstandingNotice);
			
			for(Book book : outstandingBooks){
				Paragraph bookParagraph = makeBookTable(writer, book);
				addEmptyLine(bookParagraph,1);
				pdf.add(bookParagraph);
			}
		}

		//TODO this should be placed in its own method
		Paragraph   dateOfTransactionDiv = new Paragraph();
		PdfPTable   dateOfTransactionTable = new PdfPTable(2);
		dateOfTransactionDiv.add(dateOfTransactionTable);
		dateOfTransactionTable.setWidthPercentage(100);
		dateOfTransactionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		dateOfTransactionTable.getDefaultCell().setPadding(10);
		dateOfTransactionTable.addCell(new Paragraph("Date of Return"));
		dateOfTransactionTable.addCell(new Paragraph(DateUtil.getDateTime()));
		
		pdf.add(dateOfTransactionDiv);
	}
	
	private static Paragraph makeBookTable(PdfWriter writer, Book book){
		Paragraph   returnedBooksDiv = new Paragraph();
		PdfPTable   returnedBooksTable = new PdfPTable(2);
		Properties  bookProperties = book.getPersistentState();
		
		returnedBooksDiv.add(returnedBooksTable);

		returnedBooksTable.setWidthPercentage(100);
		returnedBooksTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		returnedBooksTable.getDefaultCell().setPadding(5);

		returnedBooksTable.addCell(new Paragraph("Barcode"));
		returnedBooksTable.addCell(createBarCode39(bookProperties.getProperty("Barcode"), writer.getDirectContent()));
		returnedBooksTable.getRow(0).getCells()[1].setFixedHeight(100);

		returnedBooksTable.addCell(new Paragraph("Title"));
		returnedBooksTable.addCell(new Paragraph(bookProperties.getProperty("Title")));

		returnedBooksTable.addCell(new Paragraph("Authors"));
		returnedBooksTable.addCell(new Paragraph(bookProperties.getProperty("Author1")));

		returnedBooksTable.addCell(new Paragraph("ISBN"));
		returnedBooksTable.addCell(new Paragraph(bookProperties.getProperty("ISBN")));

		returnedBooksTable.addCell(new Paragraph("Price"));
		returnedBooksTable.addCell(new Paragraph(bookProperties.getProperty("SuggestedPrice")));

		return returnedBooksDiv;
		
		
	}
	
	
	
	
	

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static void addMetaData(String title, String subtitle, Worker worker, Document document)
	{
		Properties  workerDatas = worker.getPersistentState();

		document.addTitle(title);
		document.addSubject(subtitle);
		document.addAuthor(workerDatas.getProperty("FirstName") + " " + workerDatas.getProperty("LastName"));
		document.addCreator("Librerian");
	}

	private static void addTitleLostBook(Document pdf) throws DocumentException
	{
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Lost Book", TITLE_FONT));
		title.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(title,1);
		pdf.add(title);
	}
	
	private static void addTitleReturn(Document pdf) throws DocumentException
	{
		Paragraph title = new Paragraph();
		title.add(new Paragraph("Return Book(s)", TITLE_FONT));
		title.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(title,1);
		pdf.add(title);
	}

	private static Image createBarCode39(String myText, PdfContentByte directContent) {
		/**
		 * Code 39 character set consists of barcode symbols representing
		 * characters 0-9, A-Z, the space character and the following symbols:
		 * - . $ / + %
		 */

		Barcode39 myBarCode39 = new Barcode39();
		myBarCode39.setCode(myText);
		myBarCode39.setStartStopText(false);
		Image myBarCodeImage39 = myBarCode39.createImageWithBarcode(directContent, null, null);
		return myBarCodeImage39;
	}

	public static boolean generate(String action, String location, Book book, Borrower borrower, Worker worker)
	{
		Document    pdf = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream(location));
			pdf.open();
			PDFGenerator.class.getMethod(action, writer.getClass(), pdf.getClass(), book.getClass(), borrower.getClass(), worker.getClass())
			.invoke(null, writer, pdf, book, borrower, worker);
			pdf.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean generate(String action, String location, ArrayList<Book> returnedBooks, ArrayList<Book> outstandingBooks, Borrower borrower, Worker worker)
	{
		Document    pdf = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream(location));
			pdf.open();
			PDFGenerator.class.getMethod(action, writer.getClass(), pdf.getClass(), returnedBooks.getClass(), outstandingBooks.getClass(), borrower.getClass(), worker.getClass())
			.invoke(null, writer, pdf, returnedBooks, outstandingBooks, borrower, worker);
			pdf.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void generateLostBookPDFContent(PdfWriter writer, Document pdf, Book book, Borrower borrower, Worker worker)
	{
		try {
			addMetaData("Lost Book", "Receipt for transaction",worker,pdf);
			addTitleLostBook(pdf);
			addContentActors(pdf, borrower,worker);
			addContentLostBook(writer, pdf, book, borrower, worker);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateReturnPDFContent(PdfWriter writer, Document pdf, ArrayList<Book> returnedBooks, ArrayList<Book> outstandingBooks, Borrower borrower, Worker worker)
	{
		try {
			addMetaData("Return Book(s)", "Receipt for transaction",worker,pdf);
			addTitleReturn(pdf);
			addContentActors(pdf, borrower,worker);
			addContentReturn(writer, pdf, returnedBooks, outstandingBooks, borrower, worker);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
