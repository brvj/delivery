package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.jpa.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ActionRepository extends JpaRepository<Action, Long> {
    @Query("select a from Action a where a.store.id = ?1")
    Page<Action> findActionByStore_Id(Long id, Pageable pageable);

    @Query("select a from Action a left join a.articles articles where articles.id = ?1")
    Page<Action> findActionsByArticle_Id(Long id, Pageable pageable);
}