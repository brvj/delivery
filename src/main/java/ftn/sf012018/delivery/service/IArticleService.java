package ftn.sf012018.delivery.service;

import ftn.sf012018.delivery.model.dto.ArticleRequestDTO;
import ftn.sf012018.delivery.model.dto.ArticleResponseDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.query.ArticleQueryOptions;
import org.springframework.data.domain.Pageable;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public interface IArticleService {

    void index(Article article);

    void delete(ArticleRequestDTO articleDTO) throws IOException;

    void update(ArticleRequestDTO articleDTO) throws IOException;

    void reindex();

    Set<ArticleResponseDTO> getByStore(StoreDTO storeDTO, Pageable pageable);

    Set<ArticleResponseDTO> getByStoreAndCustomQuery(ArticleQueryOptions articleQueryOptions);

    int indexUnitFromFile(File file);

    void indexUploadedFile(ArticleRequestDTO articleDTO) throws IOException;

    File getResourceFilePath(String path);
}
