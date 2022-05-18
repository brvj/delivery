package ftn.sf012018.delivery.contract.repository.user;

import ftn.sf012018.delivery.model.mappings.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
    Customer findByUsernameAndPassword(String username, String password);

    Page<Customer> findByBlocked(Boolean blocked, Pageable pageable);

    Customer findByUsernameAndBlocked(String username, boolean blocked);
}