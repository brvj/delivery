package ftn.sf012018.delivery.mapper;

import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.contract.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class ActionMapper {
    @Autowired
    StoreMapper storeMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleRepository articleRepository;

    public ActionDTO mapToDTO(Action source){
        if(source == null) return null;

        return ActionDTO.builder()
                .id(source.getId())
                .percentage(source.getPercentage())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .text(source.getText())
                .storeDTO(storeMapper.mapToDTO(source.getStore()))
                .articleDTOS(articleMapper.mapToDTO(source.getArticles()))
                .build();
    }

    public Action mapModel(ActionDTO source) throws IOException {
        if(source == null) return null;

        return Action.builder()
                .id(source.getId())
                .percentage(source.getPercentage())
                .startDate(source.getStartDate())
                .endDate(source.getEndDate())
                .text(source.getText())
                .store(storeMapper.mapModel(source.getStoreDTO()))
                .articles(articleMapper.mapModelResponse(source.getArticleDTOS()))
                .build();
    }

    public Set<ActionDTO> mapToDTO(Set<Action> source){
        if(source.isEmpty()) return null;

        Set<ActionDTO> result = new HashSet<>();
        for (Action a : source) result.add(mapToDTO(a));

        return result;
    }

    public Set<Action> mapModel(Set<ActionDTO> source) throws IOException {
        if(source.isEmpty()) return null;

        Set<Action> result = new HashSet<>();
        for (ActionDTO a : source) result.add(mapModel(a));

        return result;
    }
}
