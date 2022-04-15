package ftn.sf012018.delivery.service;

import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.mappings.user.Store;
import ftn.sf012018.delivery.model.query.ArticleQueryOptions;
import org.springframework.data.domain.Page;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface IArticleService {

    void index(Article article);

    void delete(ArticleDTO articleDTO);

    void update(ArticleDTO articleDTO, ArticleDTO articleDTOOld) throws IOException;

    void reindex();

    Set<ArticleDTO> getByStore(StoreDTO storeDTO);

    Set<ArticleDTO> getByStoreAndCustomQuery(ArticleQueryOptions articleQueryOptions);

    int indexUnitFromFile(File file);

    void indexUploadedFile(ArticleDTO articleDTO) throws IOException;

    File getResourceFilePath(String path);
}
