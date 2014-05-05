package common;

import java.util.ArrayList;

import model.Book;
import model.BookDueDate;
import model.Borrower;
import model.Worker;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Created by chaber_e on 23/04/2014.
 * For Package common
 */
public class PDFGeneratorBackBook extends PDFGenerator {
	@Override
	protected void addContent(PdfWriter writer, Document pdf, ArrayList<Book> returnedBooks, ArrayList<Book> outstandingBooks, Borrower borrower, Worker worker, BookDueDate dueDate) throws DocumentException
	{
		Paragraph subTitle;

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
		pdf.add(addContentDate("Date of return"));
	}

	@Override
	protected String getTitle() {
		return "Return Book(s)";
	}

	@Override
	protected String getSubleTitle() {
		return "Returned Book Details";
	}
}
