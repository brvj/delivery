package ftn.sf012018.delivery.converter;

import ftn.sf012018.delivery.converter.user.StoreConverter;
import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.repository.ArticleRepository;
import ftn.sf012018.delivery.repository.user.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ActionConverter {
    @Autowired
    StoreConverter storeConverter;

    @Autowired
    ArticleConverter articleConverter;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ArticleRepository articleRepository;

    public ActionDTO convertToDTO(Action source){
        if(source == null) return null;

        return new ActionDTO(source.getId(), source.getPercentage(), source.getStartDate(), source.getEndDate(),
                source.getText(), storeConverter.convertToDTO(source.getStore()),
                articleConverter.convertToDTO(source.getArticles()));
    }

    public Action convertToJPA(ActionDTO source){
        if(source == null) return null;

        if(source.getStoreDTO() == null || !storeRepository.existsById(source.getStoreDTO().getId()) ||
                source.getArticleDTOS().isEmpty()) throw new IllegalArgumentException();

        Action action = new Action();

        action.setId(source.getId());
        action.setPercentage(source.getPercentage());
        action.setStartDate(source.getStartDate());
        action.setEndDate(source.getEndDate());
        action.setText(source.getText());
        action.setStore(storeRepository.findStoreByAction_Id(source.getId()).get());
        action.setArticles((Set<Article>) articleRepository.findArticleByAction_Id(source.getId(), Pageable.unpaged()));

        return action;
    }

    public Set<ActionDTO> convertToDTO(Set<Action> source){
        if(source.isEmpty()) return null;

        Set<ActionDTO> result = new HashSet<>();
        for (Action a : source) result.add(convertToDTO(a));

        return result;
    }

    public Set<Action> convertToJPA(Set<ActionDTO> source){
        if(source.isEmpty()) return null;

        Set<Action> result = new HashSet<>();
        for (ActionDTO a : source) result.add(convertToJPA(a));

        return result;
    }
}
