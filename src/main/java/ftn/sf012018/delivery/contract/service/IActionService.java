package ftn.sf012018.delivery.contract.service;

import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface IActionService {
    void index (ActionDTO actionDTO) throws IOException;

    Page<ActionDTO> getByStoreAndCurrentDate(StoreDTO storeDTO, Pageable pageable);

    ActionDTO getById(String id);
}
