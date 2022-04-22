package ftn.sf012018.delivery.service.user;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IStoreService {

    void index(StoreDTO storeDTO);

    void update(StoreDTO storeNew);

    StoreDTO getByUsernameAndPassword(String username, String password);

    Set<StoreDTO> getAll(Pageable pageable);

    Store getByUsernameAndBlocked(String username);
}
