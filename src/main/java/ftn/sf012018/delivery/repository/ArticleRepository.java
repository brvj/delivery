package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, String> {
    Page<Article> findByStore(Store store, Pageable pageable);

    Optional<Article> findById(String id);
}