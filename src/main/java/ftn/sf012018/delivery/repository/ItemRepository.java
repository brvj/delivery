package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}