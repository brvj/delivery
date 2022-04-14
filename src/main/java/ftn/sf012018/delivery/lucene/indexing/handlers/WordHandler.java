package ftn.sf012018.delivery.lucene.indexing.handlers;

import ftn.sf012018.delivery.model.mappings.Article;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class WordHandler extends DocumentHandler {

	public Article getIndexUnit(File file) {
		Article retVal = new Article();
		InputStream is;

		try {
			is = new FileInputStream(file);
			WordExtractor we = new WordExtractor(is);
			String text = we.getText();
			retVal.setDescription(text);
			
			SummaryInformation si = we.getSummaryInformation();
			String title = si.getTitle();
			retVal.setName(title);

			String keywords = si.getKeywords();
			retVal.setKeywords(keywords);
			
			retVal.setFilename(file.getCanonicalPath());
			
			we.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Dokument ne postoji");
		} catch (Exception e) {
			System.out.println("Problem pri parsiranju doc fajla");
		}

		return retVal;
	}

	@Override
	public String getDescription(File file) {
		String text = null;
		try {
			WordExtractor we = new WordExtractor(new FileInputStream(file));
			text = we.getText();
			we.close();
		} catch (FileNotFoundException e1) {
			System.out.println("Dokument ne postoji");
		} catch (Exception e) {
			System.out.println("Problem pri parsiranju doc fajla");
		}
		return text;
	}

}
