package ftn.sf012018.delivery.service.impl;

import ftn.sf012018.delivery.mapper.ActionMapper;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.repository.ActionRepository;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrStore;
import ftn.sf012018.delivery.security.annotations.AuthorizeAny;
import ftn.sf012018.delivery.service.IActionService;
import ftn.sf012018.delivery.util.DiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
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
    @AuthorizeAdminOrStore
    public void index(ActionDTO actionDTO) {
        for (ArticleDTO article : actionDTO.getArticleDTOS()){
            article.setPrice(DiscountCalculator.calculate(article.getPrice(), actionDTO.getPercentage()));
        }

        actionRepository.save(actionMapper.mapModel(actionDTO));
    }

    @Override
    @AuthorizeAny
    public Set<ActionDTO> getByStoreAndCurrentDate(StoreDTO storeDTO, Pageable pageable) {
        LocalDate currentDate = LocalDate.now();

        return actionMapper.mapToDTO(actionRepository.findByStoreAndStartDateGreaterThanAndEndDateLessThan(
                storeMapper.mapModel(storeDTO), currentDate, currentDate, pageable).toSet());
    }
}
