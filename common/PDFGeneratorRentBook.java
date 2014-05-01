package common;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import model.Book;
import model.BookDueDate;
import model.Borrower;
import model.Worker;

import java.util.ArrayList;

/**
 * Created by chaber_e on 23/04/2014.
 * For Package common
 */
public class PDFGeneratorRentBook extends PDFGenerator {
	@Override
	protected void addContent(PdfWriter writer, Document pdf, ArrayList<Book> books1, ArrayList<Book> books2, Borrower borrower, Worker worker, BookDueDate dueDate) throws DocumentException
	{
		for(Book book : books1){
			Paragraph bookParagraph = makeBookTable(writer, book);
			addEmptyLine(bookParagraph,1);
			pdf.add(bookParagraph);
		}

		Paragraph terms = new Paragraph();
		terms.add("By signing below, I acknowledge that I have recieved these items and I agree to return the books by " +
				          dueDate.getState("DueDate") + ". I acknowledge that I am responsible for the replacement of those " +
				          "books that are not returned by this date.");
		addEmptyLine(terms,1);
		pdf.add(terms);

		pdf.add(addContentDate("Date of rent"));

		Paragraph signature = new Paragraph();
		signature.add("Signature: ___________________________________________");
		pdf.add(signature);
	}

	@Override
	protected String getTitle()
	{
		return "Rent Book(s)";
	}

	@Override
	protected String getSubleTitle()
	{
		return "Rented Book Details";
	}
}
