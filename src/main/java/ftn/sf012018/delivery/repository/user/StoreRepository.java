package ftn.sf012018.delivery.repository.user;

import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends ElasticsearchRepository<Store, String> {
}