package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.jpa.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a left join a.items items where items.id = ?1")
    Optional<Article> findArticleByItem_Id(Long id);

    @Query("select a from Article a where a.store.id = ?1")
    Page<Article> findArticleByStore_Id(Long id, Pageable pageable);

    @Query("select a from Article a left join a.actions actions where actions.id = ?1")
    Page<Article> findArticleByAction_Id(Long id, Pageable pageable);
}