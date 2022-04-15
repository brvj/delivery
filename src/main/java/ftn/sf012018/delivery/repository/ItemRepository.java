package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends ElasticsearchRepository<Item, Long> {
}