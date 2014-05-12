package document;

import java.util.List;

import model.Book;
import model.Borrower;
import model.Worker;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;

import controller.Controller;

public abstract class Receipt extends PDF {

	protected final Controller controller;

	protected Receipt(String title, Controller controller) {
		super(title);
		this.controller = controller;
	}

	protected Paragraph createActorsPanel(Borrower borrower,Worker worker){
		Paragraph actorsPanel = new Paragraph();

		PdfPTable actorsTable = new PdfPTable(2);
		actorsTable.setWidthPercentage(100);
		actorsTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		actorsTable.getDefaultCell().setPadding(10);
		actorsPanel.add(actorsTable);

		PdfPTable borrowerTable = new PdfPTable(1);
		borrowerTable.getDefaultCell().setBorder(0);
		borrowerTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		borrowerTable.addCell(createSubTitle("Borrower"));
		borrowerTable.addCell(createParagraph((String)borrower.getState("Name")));
		borrowerTable.addCell(createParagraph((String)borrower.getState("BannerID")));
		borrowerTable.addCell(createParagraph((String)borrower.getState("Email")));
		borrowerTable.addCell(createParagraph("$" + borrower.getState("MonetaryPenalty")));
		actorsTable.addCell(borrowerTable);

		PdfPTable workerTable = new PdfPTable(1);
		workerTable.getDefaultCell().setBorder(0);
		workerTable.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

		workerTable.addCell(createSubTitle("Worker"));
		workerTable.addCell(createParagraph((String)worker.getState("Name")));
		workerTable.addCell(createParagraph((String)worker.getState("BannerID")));
		workerTable.addCell(createParagraph((String)worker.getState("Email")));
		workerTable.addCell(createParagraph((String)worker.getState("ContactPhone")));
		actorsTable.addCell(workerTable);

		actorsTable.getRow(0).getCells()[0].setPaddingLeft(0);
		actorsTable.getRow(0).getCells()[1].setHorizontalAlignment(Element.ALIGN_RIGHT);
		actorsTable.getRow(0).getCells()[1].setPaddingRight(0);

		addEmptyLine(actorsPanel);

		return actorsPanel;
	}

	protected Paragraph createBookPanel(Book book) {
		Paragraph bookPanel = new Paragraph();

		PdfPTable bookTable = new PdfPTable(2);
		bookTable.setWidthPercentage(100);
		bookTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		bookTable.getDefaultCell().setPadding(5);

		bookTable.addCell(createParagraph("Barcode"));
		bookTable.addCell(createBarCode((String)book.getState("Barcode")));
		bookTable.getRow(0).getCells()[1].setFixedHeight(40);

		bookTable.addCell(createParagraph("Title"));
		bookTable.addCell(createParagraph((String)book.getState("Title")));

		bookTable.addCell(createParagraph("Author"));
		bookTable.addCell(createParagraph((String)book.getState("Author1")));

		bookTable.addCell(createParagraph("ISBN"));
		bookTable.addCell(createParagraph((String)book.getState("ISBN")));

		bookTable.addCell(createParagraph("Price"));
		bookTable.addCell(createParagraph((String)book.getState("SuggestedPrice")));

		bookPanel.add(bookTable);

		return bookPanel;
	}

	protected Paragraph createBooksTable(List<Book> books) {
		Paragraph container = new Paragraph();
		for(Book book : books){
			container.add(createBookPanel(book));
			addEmptyLine(container);
		}
		return container;
	}
}
