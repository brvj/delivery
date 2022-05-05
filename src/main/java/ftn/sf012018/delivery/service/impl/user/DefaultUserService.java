package ftn.sf012018.delivery.service.impl.user;

import ftn.sf012018.delivery.lucene.search.QueryBuilderCustom;
import ftn.sf012018.delivery.mapper.user.CustomerMapper;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.dto.user.PasswordUpdateDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;
import ftn.sf012018.delivery.model.mappings.user.Customer;
import ftn.sf012018.delivery.model.mappings.user.Store;
import ftn.sf012018.delivery.security.CustomPrincipal;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrStore;
import ftn.sf012018.delivery.service.user.IDefaultUserService;
import ftn.sf012018.delivery.util.SearchType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultUserService implements IDefaultUserService, UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getByUsernameAndBlocked(username);
        Store store = storeService.getByUsernameAndBlocked(username);
        Customer customer = customerService.getByUsernameAndBlocked(username);

        if (admin == null && store == null && customer == null)
            throw new UsernameNotFoundException(String.format("User with username: %s not found", username));

        boolean isAdmin = false;
        boolean isStore = false;
        boolean isCustomer = false;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (admin != null){
            grantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            isAdmin = true;
        } else if(store != null){
            grantedAuthorities.add(new SimpleGrantedAuthority("STORE"));
            isStore = true;
        } else if(customer != null){
            grantedAuthorities.add(new SimpleGrantedAuthority("CUSTOMER"));
            isCustomer = true;
        }

        String ownerId = null;

        if(isAdmin){
            ownerId = admin.getId();

            return new CustomPrincipal(admin.getId(), admin.getUsername(), admin.getPassword(), admin.getFirstname(),
                    admin.getLastname(), ownerId, grantedAuthorities);

        } else if(isStore){
            ownerId = store.getId();

            return new CustomPrincipal(store.getId(), store.getUsername(), store.getPassword(), store.getFirstname(),
                    store.getLastname(), ownerId, grantedAuthorities);

        } else if(isCustomer){
            ownerId = customer.getId();

            return new CustomPrincipal(customer.getId(), customer.getUsername(), customer.getPassword(), customer.getFirstname(),
                    customer.getLastname(), ownerId, grantedAuthorities);
        }

        return null;
    }

    @Override
    public void indexAdmin(AdminDTO adminDTO) {
        adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));

        adminService.index(adminDTO);
    }

    @Override
    public void indexStore(StoreDTO storeDTO) {
        storeDTO.setPassword(passwordEncoder.encode(storeDTO.getPassword()));

        storeService.index(storeDTO);
    }

    @Override
    public void indexCustomer(CustomerDTO customerDTO) {
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));

        customerService.index(customerDTO);
    }

    @Override
    public void updateStore(StoreDTO storeDTO) {
        storeService.update(storeDTO);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) { customerService.update(customerDTO); }

    @Override
    public void changeStorePassword(PasswordUpdateDTO passwordUpdateDTO) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", passwordUpdateDTO.getUserId()))
                .build();

        SearchHits<Store> stores =
                elasticsearchRestTemplate.search(searchQuery, Store.class, IndexCoordinates.of("stores"));

        Store store = stores.getSearchHit(0).getContent();

        if (passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), store.getPassword()) &&
                passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getRepeatedNewPassword())) {
            {
                store.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));

                storeService.index(storeMapper.mapToDTO(store));
            }
        }
    }

    @Override
    public void changeCustomerPassword(PasswordUpdateDTO passwordUpdateDTO) {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilderCustom.buildQuery(SearchType.MATCH, "_id", passwordUpdateDTO.getUserId()))
                .build();

        SearchHits<Customer> customers =
                elasticsearchRestTemplate.search(searchQuery, Customer.class, IndexCoordinates.of("customers"));

        Customer customer = customers.getSearchHit(0).getContent();

        if (passwordEncoder.matches(passwordUpdateDTO.getOldPassword(), customer.getPassword())&&
            passwordUpdateDTO.getNewPassword().equals(passwordUpdateDTO.getRepeatedNewPassword()))
        {
            customer.setPassword(passwordEncoder.encode(passwordUpdateDTO.getNewPassword()));

            customerService.index(customerMapper.mapToDTO(customer));
        }
    }

    @AuthorizeAdminOrStore
    public void test() {
    }
}
