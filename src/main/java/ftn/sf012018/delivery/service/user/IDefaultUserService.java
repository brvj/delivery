package ftn.sf012018.delivery.service.user;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.dto.user.PasswordUpdateDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;

public interface IDefaultUserService {

    void indexAdmin(AdminDTO adminDTO);

    void indexStore(StoreDTO storeDTO);

    void indexCustomer(CustomerDTO customerDTO);

    void updateStore(StoreDTO storeDTO);

    void updateCustomer(CustomerDTO customerDTO);

    void changeStorePassword(PasswordUpdateDTO passwordUpdateDTO);

    void changeCustomerPassword(PasswordUpdateDTO passwordUpdateDTO);
}
