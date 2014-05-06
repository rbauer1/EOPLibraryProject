package document;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import utilities.DateUtil;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public abstract class PDF {

	protected final static Font TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	protected final static Font SUB_TITLE_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
	protected final static Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN,12,Font.NORMAL);

	protected Document document;

	protected PdfWriter writer;

	private final String title;

	protected PDF(String title) {
		this.title = title;
		document = new Document();
		document.addTitle(title);
	}

	protected void addEmptyLine(Paragraph paragraph) {
		paragraph.add(createParagraph(" "));
	}

	protected abstract void build() throws DocumentException;

	protected void buildTitle() throws DocumentException{
		document.add(createTitle(title));
	}

	protected Image createBarCode(String code) {
		Barcode39 barcode39 = new Barcode39();
		barcode39.setCode(code);
		barcode39.setStartStopText(false);
		return barcode39.createImageWithBarcode(writer.getDirectContent(), null, null);
	}

	protected Paragraph createDate(String label) {
		Paragraph container = new Paragraph();

		PdfPTable dateTable = new PdfPTable(2);
		dateTable.setWidthPercentage(100);
		dateTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		dateTable.getDefaultCell().setPadding(10);
		dateTable.addCell(createParagraph(label));
		dateTable.addCell(createParagraph(DateUtil.getDateTime()));

		container.add(dateTable);
		return container;
	}

	protected Paragraph createParagraph(String text){
		return new Paragraph(text, NORMAL_FONT);
	}

	protected Paragraph createSubTitle(String text){
		Paragraph subTitle = new Paragraph();
		subTitle.add(new Paragraph(text, SUB_TITLE_FONT));
		subTitle.setAlignment(Element.ALIGN_LEFT);
		return subTitle;
	}

	protected Paragraph createTitle(String text){
		Paragraph subTitle = new Paragraph();
		subTitle.add(new Paragraph(text, TITLE_FONT));
		subTitle.setAlignment(Element.ALIGN_CENTER);
		addEmptyLine(subTitle);
		return subTitle;
	}

	public boolean save(String filePath){
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			buildTitle();
			build();
			document.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			writer = null;
		}
		return false;
	}
}
