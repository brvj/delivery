package ftn.sf012018.delivery.contract.repository.user;

import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends ElasticsearchRepository<Store, String> {
    Store findByUsernameAndPassword(String username, String password);

    Store findByUsernameAndBlocked(String username, boolean blocked);

    Optional<Store> findById(String id);
}