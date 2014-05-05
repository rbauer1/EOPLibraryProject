package common;

import java.util.ArrayList;

import model.Book;
import model.BookDueDate;
import model.Borrower;
import model.Worker;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by chaber_e on 23/04/2014.
 * For Package common
 */
public class PDFGeneratorLostBook extends PDFGenerator {
	@Override
	protected void addContent(PdfWriter writer, Document pdf, ArrayList<Book> books1, ArrayList<Book> unused, Borrower borrower, Worker worker, BookDueDate dueDate) throws DocumentException
	{
		pdf.add(makeBookTable(writer,books1.get(0)));
	}

	@Override
	protected String getTitle() {
		return "Lost Book";
	}

	@Override
	protected String getSubleTitle() {
		return "Lost Book Details";
	}
}
