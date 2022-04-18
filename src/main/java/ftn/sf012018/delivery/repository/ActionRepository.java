package ftn.sf012018.delivery.repository;

import ftn.sf012018.delivery.model.mappings.Action;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends ElasticsearchRepository<Action, String> {
}