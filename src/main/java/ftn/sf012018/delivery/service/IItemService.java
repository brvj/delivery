package ftn.sf012018.delivery.service;

import ftn.sf012018.delivery.model.dto.ItemDTO;

import java.util.Set;

public interface IItemService {
    void index (Set<ItemDTO> itemDTOS);
}
