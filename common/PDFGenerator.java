package common;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;

import model.Book;
import model.BookDueDate;
import model.Borrower;
import model.Worker;
import utilities.DateUtil;

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

public abstract class PDFGenerator {
	protected final static Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
	protected final static Font SUB_TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);

	protected abstract void     addContent(PdfWriter writer, Document pdf, ArrayList<Book> books1, ArrayList<Book> books2,Borrower borrower, Worker worker, BookDueDate dueDate) throws DocumentException;
	protected abstract String   getTitle();
	protected abstract String   getSubleTitle();

	public boolean generate(String location, ArrayList<Book> books1, ArrayList<Book> books2, Borrower borrower, Worker worker, BookDueDate dueDate)
	{
		Document    pdf = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream(location));
			pdf.open();
			generateContent(writer,pdf,books1,books2,borrower,worker,dueDate);
			pdf.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected void generateContent(PdfWriter writer, Document pdf, ArrayList<Book> books1, ArrayList<Book> books2, Borrower borrower, Worker worker, BookDueDate dueDate)
	{
		try {
			addMetaData(getTitle(), getSubleTitle(),worker,pdf);
			addTitle(pdf);
			addContentActors(pdf, borrower,worker);
			addSubTitle(pdf);
			addContent(writer, pdf, books1, books2, borrower, worker, dueDate);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	protected void addTitle(Document pdf) throws DocumentException
	{
		Paragraph title = new Paragraph();
		title.add(new Paragraph(getTitle(), TITLE_FONT));
		title.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(title,1);
		pdf.add(title);
	}

	protected void addSubTitle(Document pdf) throws DocumentException
	{
		Paragraph   subTitle = new Paragraph();

		subTitle.add(new Paragraph(getSubleTitle(), SUB_TITLE_FONT));
		subTitle.setAlignment(Element.ALIGN_LEFT);
		addEmptyLine(subTitle,1);
		pdf.add(subTitle);
	}

	protected static void addMetaData(String title, String subtitle, Worker worker, Document document)
	{
		Properties  workerDatas = worker.getPersistentState();

		document.addTitle(title);
		document.addSubject(subtitle);
		document.addAuthor(workerDatas.getProperty("FirstName") + " " + workerDatas.getProperty("LastName"));
		document.addCreator("Librerian");
	}

	protected static void addContentActors(Document pdf, Borrower borrower, Worker worker) throws DocumentException
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
		borrowerTable.addCell(new Paragraph(borrowerProperties.getProperty("BannerID")));
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

	protected static Paragraph addContentDate(String dateType)
	{
		Paragraph   dateOfTransactionDiv = new Paragraph();
		PdfPTable   dateOfTransactionTable = new PdfPTable(2);
		dateOfTransactionDiv.add(dateOfTransactionTable);
		dateOfTransactionTable.setWidthPercentage(100);
		dateOfTransactionTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		dateOfTransactionTable.getDefaultCell().setPadding(10);
		dateOfTransactionTable.addCell(new Paragraph(dateType));
		dateOfTransactionTable.addCell(new Paragraph(DateUtil.getDateTime()));
		return dateOfTransactionDiv;
	}

	protected static Paragraph makeBookTable(PdfWriter writer, Book book)
	{
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

	protected static void addEmptyLine(Paragraph paragraph, int number)
	{
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	protected static Image createBarCode39(String myText, PdfContentByte directContent)
	{
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
}
