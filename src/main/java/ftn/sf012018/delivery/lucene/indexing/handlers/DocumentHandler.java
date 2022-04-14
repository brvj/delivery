package ftn.sf012018.delivery.lucene.indexing.handlers;

import ftn.sf012018.delivery.model.mappings.Article;

import java.io.File;

public abstract class DocumentHandler {
	/**
	 * Od prosledjene datoteke se konstruise Lucene Document
	 * 
	 * @param file
	 *            datoteka u kojoj se nalaze informacije
	 * @return Lucene Document
	 */
	public abstract Article getIndexUnit(File file);
	public abstract String getDescription(File file);

}
