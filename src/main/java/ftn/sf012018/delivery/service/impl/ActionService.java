package ftn.sf012018.delivery.service.impl;

import ftn.sf012018.delivery.mapper.ActionMapper;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.repository.ActionRepository;
import ftn.sf012018.delivery.service.IActionService;
import ftn.sf012018.delivery.util.DiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
public class ActionService implements IActionService {
    @Autowired
    private ActionRepository actionRepository;

    @Autowired
    private ActionMapper actionMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public void index(ActionDTO actionDTO) {
        for (ArticleDTO article : actionDTO.getArticleDTOS()){
            article.setPrice(DiscountCalculator.calculate(article.getPrice(), actionDTO.getPercentage()));
        }

        actionRepository.save(actionMapper.mapModel(actionDTO));
    }

    @Override
    public Set<ActionDTO> getByStoreAndCurrentDate(StoreDTO storeDTO, Pageable pageable) {
        return actionMapper.mapToDTO(actionRepository.findByStore(storeMapper.mapModel(storeDTO), pageable).toSet());
    }
}
