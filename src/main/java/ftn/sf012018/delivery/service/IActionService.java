package ftn.sf012018.delivery.service;

import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IActionService {
    void index (ActionDTO actionDTO);

    Set<ActionDTO> getByStoreAndCurrentDate(StoreDTO storeDTO, Pageable pageable);
}
