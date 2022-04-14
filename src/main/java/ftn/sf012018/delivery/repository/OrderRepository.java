package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}