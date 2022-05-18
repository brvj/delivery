package ftn.sf012018.delivery.contract.repository;

import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ActionRepository extends ElasticsearchRepository<Action, String> {
    Page<Action> findByStoreAndStartDateGreaterThanAndEndDateLessThan(Store store, LocalDate currentDate1,
                                                                      LocalDate currentDate2, Pageable pageable);
}