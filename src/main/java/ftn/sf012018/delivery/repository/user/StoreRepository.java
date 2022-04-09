package ftn.sf012018.delivery.repository.user;

import ftn.sf012018.delivery.model.jpa.user.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("select s from Store s left join s.articles articles where articles.id = ?1")
    Optional<Store> findStoreByArticle_Id(Long id);

    @Query("select s from Store s left join s.actions actions where actions.id = ?1")
    Optional<Store> findStoreByAction_Id(Long id);
}