package ftn.sf012018.delivery.service.user;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.mappings.user.Customer;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface ICustomerService {

    void index(CustomerDTO customer);

    void update(CustomerDTO customer);

    void block(String id);

    Set<CustomerDTO> findAllBlockedCustomers(boolean blocked, Pageable pageable);

    Set<CustomerDTO> findAllUnblockedCustomers(boolean blocked, Pageable pageable);

    CustomerDTO findByUsernameAndPassword(String userName, String password);

    Customer findByUsername(String username);
}
