package ftn.sf012018.delivery.contract.service.user;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.mappings.user.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService {

    void index(CustomerDTO customer);

    void update(CustomerDTO customer);

    void block(String id);

    Page<CustomerDTO> getAllBlockedCustomers(boolean blocked, Pageable pageable);

    Page<CustomerDTO> getAllUnblockedCustomers(boolean blocked, Pageable pageable);

    CustomerDTO getByUsernameAndPassword(String userName, String password);

    Customer getByUsernameAndBlocked(String username);

    CustomerDTO getById(String id);
}
