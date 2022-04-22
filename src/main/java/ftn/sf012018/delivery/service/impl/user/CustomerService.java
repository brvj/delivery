package ftn.sf012018.delivery.service.impl.user;

import ftn.sf012018.delivery.lucene.search.QueryBuilderCustom;
import ftn.sf012018.delivery.mapper.user.CustomerMapper;
import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.mappings.user.Customer;
import ftn.sf012018.delivery.repository.user.CustomerRepository;
import ftn.sf012018.delivery.service.user.ICustomerService;
import ftn.sf012018.delivery.util.SearchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public void index(CustomerDTO customer) {
        customerRepository.save(customerMapper.mapModel(customer));
    }

    @Override
    public void update(CustomerDTO customerNew) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", customerNew.getId()))
                .build();

        SearchHits<Customer> customers =
                elasticsearchRestTemplate.search(searchQuery, Customer.class, IndexCoordinates.of("customers"));
        Customer customer = customers.getSearchHit(0).getContent();

        customer.setFirstname(customerNew.getFirstname());
        customer.setLastname(customerNew.getLastname());
        customer.setAddress(customerNew.getAddress());

        index(customerMapper.mapToDTO(customer));
    }

    @Override
    public void block(String id) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", id))
                .build();

        SearchHits<Customer> customers =
                elasticsearchRestTemplate.search(searchQuery, Customer.class, IndexCoordinates.of("customers"));
        Customer customer = customers.getSearchHit(0).getContent();

        customer.setBlocked(true);

        index(customerMapper.mapToDTO(customer));
    }

    @Override
    public Set<CustomerDTO> findAllBlockedCustomers(boolean blocked, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByBlocked(blocked, pageable);

        return customers.map(customer -> customerMapper.mapToDTO(customer)).toSet();
    }

    @Override
    public Set<CustomerDTO> findAllUnblockedCustomers(boolean blocked, Pageable pageable) {
        Page<Customer> customers = customerRepository.findByBlocked(blocked, pageable);

        return customers.map(customer -> customerMapper.mapToDTO(customer)).toSet();
    }

    @Override
    public CustomerDTO findByUsernameAndPassword(String username, String password) {
        if(username.equals("") || password.equals(""))
            return null;

        return customerMapper.mapToDTO(customerRepository.findByUsernameAndPassword(username, password));
    }

    @Override
    public Customer findByUsernameAndBlocked(String username){
        return customerRepository.findByUsernameAndBlocked(username, Boolean.FALSE);
    }
}
