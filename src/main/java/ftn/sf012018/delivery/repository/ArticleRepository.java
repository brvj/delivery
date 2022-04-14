package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {
}