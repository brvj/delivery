package ftn.sf012018.delivery.lucene.indexing.handlers;

import ftn.sf012018.delivery.model.mappings.Article;
import org.apache.lucene.document.DateTools;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;

import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFHandler extends DocumentHandler {

	@Override
	public Article getIndexUnit(File file) {
		Article retVal = new Article();
		try {
			PDFParser parser = new PDFParser((RandomAccessRead) new RandomAccessFile(file, "r"));
			parser.parse();
			String text = getDescription(parser);
			retVal.setDescription(text);

			// metadata extraction
			PDDocument pdf = parser.getPDDocument();
			PDDocumentInformation info = pdf.getDocumentInformation();

			String title = ""+info.getTitle();
			retVal.setDescription(title);

			String keywords = ""+info.getKeywords();
			retVal.setKeywords(keywords);
			
			retVal.setFilename(file.getCanonicalPath());
			
			String modificationDate= DateTools.dateToString(new Date(file.lastModified()), DateTools.Resolution.DAY);

			pdf.close();
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}

		return retVal;
	}

	@Override
	public String getDescription(File file) {
		try {
			PDFParser parser = new PDFParser((RandomAccessRead) new RandomAccessFile(file, "r"));
			parser.parse();
			PDFTextStripper textStripper = new PDFTextStripper();
			return textStripper.getText(parser.getPDDocument());
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}
	
	public String getDescription(PDFParser parser) {
		try {
			PDFTextStripper textStripper = new PDFTextStripper();
			return textStripper.getText(parser.getPDDocument());
		} catch (IOException e) {
			System.out.println("Greksa pri konvertovanju dokumenta u pdf");
		}
		return null;
	}

}
