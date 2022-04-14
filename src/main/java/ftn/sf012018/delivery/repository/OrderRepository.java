package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.customer.id = ?1")
    Page<Order> findOrdersByCustomer_Id(Long id, Pageable pageable);

    @Query("select o from Order o left join o.items items where items.id = ?1")
    Optional<Order> findOrderByItem_Id(Long id);
}