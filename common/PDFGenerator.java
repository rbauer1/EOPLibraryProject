package common;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;
import model.Book;
import model.Borrower;
import model.Worker;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Created by chaber_e on 21/04/2014.
 * For Package common
 */
public class PDFGenerator {
	public final static String LOST_BOOK_ACTION = "generateLostBookPDFContent";
	private final static String TMP_FILE_PATH = "C:\\Users\\chaber_e\\tmp_pdf.pdf";
	private final static Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD);
	private final static Font SUB_TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN,14,Font.BOLD);


	public static void generate(String action, Book book, Borrower borrower, Worker worker)
	{
		Document    pdf = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream(TMP_FILE_PATH));
			pdf.open();
			PDFGenerator.class.getMethod(action, writer.getClass(), pdf.getClass(), book.getClass(), borrower.getClass(), worker.getClass())
					.invoke(null, writer, pdf, book, borrower, worker);
			pdf.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void generateLostBookPDFContent(PdfWriter writer, Document pdf, Book book, Borrower borrower, Worker worker)
	{
		try {
			addMetaData("Lost Book", "Receipe for transaction",worker,pdf);
			addTitleLostBook(pdf);
			addContentActors(pdf, borrower,worker);
			addContentLostBook(writer, pdf, book, borrower, worker);
		} catch (DocumentException e) {
			e.printStackTrace();
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

		subTitle.add(new Paragraph("Book details", SUB_TITLE_FONT));
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
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Price")));

		lostBookTable.addCell(new Paragraph("Notes"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("Notes")));

		lostBookTable.addCell(new Paragraph("Date"));
		lostBookTable.addCell(new Paragraph(bookProperties.getProperty("DateOfLastUpdate")));

		pdf.add(lostBookDiv);
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

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
