package ftn.sf012018.delivery.contract.service.user;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IStoreService {

    void index(StoreDTO storeDTO);

    void update(StoreDTO storeNew);

    StoreDTO getByUsernameAndPassword(String username, String password);

    Page<StoreDTO> getAll(Pageable pageable);

    Store getByUsernameAndBlocked(String username);

    StoreDTO getById(String id);
}
