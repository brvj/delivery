package ftn.sf012018.delivery.service.impl;

import ftn.sf012018.delivery.mapper.ItemMapper;
import ftn.sf012018.delivery.model.dto.ItemDTO;
import ftn.sf012018.delivery.repository.ItemRepository;
import ftn.sf012018.delivery.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ItemService implements IItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public void index(Set<ItemDTO> itemDTOS) {
        for (ItemDTO item : itemDTOS) itemRepository.save(itemMapper.mapModel(item));
    }
}
