package ftn.sf012018.delivery.service;

import ftn.sf012018.delivery.mapper.ActionMapper;
import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.dto.ArticleResponseDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.contract.repository.ActionRepository;
import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.security.annotations.AuthorizeAdminOrStore;
import ftn.sf012018.delivery.security.annotations.AuthorizeAny;
import ftn.sf012018.delivery.contract.service.IActionService;
import ftn.sf012018.delivery.util.DiscountCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDate;
import java.util.function.Function;

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
    public void index(ActionDTO actionDTO) throws IOException {
        for (ArticleResponseDTO article : actionDTO.getArticleDTOS()){
            article.setPrice(DiscountCalculator.calculate(article.getPrice(), actionDTO.getPercentage()));
        }

        actionRepository.save(actionMapper.mapModel(actionDTO));
    }

    @Override
    @AuthorizeAny
    public Page<ActionDTO> getByStoreAndCurrentDate(StoreDTO storeDTO, Pageable pageable) {
        LocalDate currentDate = LocalDate.now();

        Page<Action> actions = actionRepository.findByStoreAndStartDateGreaterThanAndEndDateLessThan(
                storeMapper.mapModel(storeDTO), currentDate, currentDate, pageable);

        return actions.map(new Function<Action, ActionDTO>() {
            @Override
            public ActionDTO apply(Action action) {
                return actionMapper.mapToDTO(action);
            }
        });
    }

    @Override
    public ActionDTO getById(String id) {
        Action action = actionRepository.findById(id).get();

        if(action != null) return actionMapper.mapToDTO(action);

        return null;
    }
}
