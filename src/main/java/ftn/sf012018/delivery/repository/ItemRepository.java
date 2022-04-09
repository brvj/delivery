package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.jpa.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.order.id = ?1")
    Page<Item> findItemsByOrder_Id(Long id, Pageable pageable);

    @Query("select i from Item i where i.article.id = ?1")
    Page<Item> findItemsByArticle_Id(Long id, Pageable pageable);
}