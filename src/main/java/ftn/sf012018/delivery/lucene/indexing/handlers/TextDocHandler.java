package ftn.sf012018.delivery.lucene.indexing.handlers;

import ftn.sf012018.delivery.model.mappings.Article;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextDocHandler extends DocumentHandler {

	@Override
	public Article getIndexUnit(File file) {
		Article retVal = new Article();
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(
					fis, StandardCharsets.UTF_8));

			String firstLine = reader.readLine(); // u prvoj liniji svake
													// tekstualne datoteke se
													// nalazi naslov rada

			retVal.setName(firstLine);
			
			/*
			 * add other custom metadata
			 */

			String secondLine = reader.readLine();
			retVal.setKeywords(secondLine);

			StringBuilder fullText = new StringBuilder();
			while (true) {
				secondLine = reader.readLine();
				if (secondLine == null) {
					break;
				}
				fullText.append(" ").append(secondLine);
			}
			retVal.setDescription(fullText.toString());
			
			retVal.setFilename(file.getCanonicalPath());
			
			return retVal;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Datoteka ne postoji");
		} catch (IOException e) {
			throw new IllegalArgumentException("Greska: Datoteka nije u redu");
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException ignored) {
				}
		}
	}

	@Override
	public String getDescription(File file) {
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			reader = new BufferedReader(new InputStreamReader(
					fis, StandardCharsets.UTF_8));
			String secondLine;
			StringBuilder fullText = new StringBuilder();
			while (true) {
				secondLine = reader.readLine();
				if (secondLine == null) {
					break;
				}
				fullText.append(" ").append(secondLine);
			}
			return fullText.toString();
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Datoteka ne postoji");
		} catch (IOException e) {
			throw new IllegalArgumentException("Greska: Datoteka nije u redu");
		} finally {
			if(reader != null)
				try {
					reader.close();
				} catch (IOException ignored) {
				}
		}
	}

}
