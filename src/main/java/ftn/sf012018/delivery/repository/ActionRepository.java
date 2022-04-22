package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends ElasticsearchRepository<Action, String> {
    Page<Action> findByStore(Store store, Pageable pageable); // TODO ubaciti da trazi i na osnovu trenutnog datuma!
}