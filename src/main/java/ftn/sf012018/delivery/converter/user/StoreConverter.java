package ftn.sf012018.delivery.converter.user;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.mappings.user.Store;
import ftn.sf012018.delivery.repository.ActionRepository;
import ftn.sf012018.delivery.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StoreConverter {
    @Autowired
    ActionRepository actionRepository;

    @Autowired
    ArticleRepository articleRepository;

    public StoreDTO convertToDTO(Store source){
        if(source == null) return null;

        return new StoreDTO(source.getId(), source.getFirstname(), source.getLastname(), source.getUsername(),
                source.getPassword(), source.isBlocked(), source.getWorkingSince(), source.getEmail(),
                source.getAddress(), source.getName());
    }

    public Store convertToJPA(StoreDTO source){
        if(source == null) return null;

        Store store = new Store();

        store.setId(source.getId());
        store.setFirstname(source.getFirstname());
        store.setLastname(source.getLastname());
        store.setUsername(source.getUsername());
        store.setPassword(source.getPassword());
        store.setBlocked(source.isBlocked());
        store.setWorkingSince(source.getWorkingSince());
        store.setEmail(source.getEmail());
        store.setAddress(source.getAddress());
        store.setName(source.getName());
        store.setActions((Set<Action>) actionRepository.findActionsByArticle_Id(source.getId(), Pageable.unpaged()));
        store.setArticles((Set<Article>) articleRepository.findArticleByStore_Id(source.getId(), Pageable.unpaged()));

        return store;
    }

    public Set<StoreDTO> convertToDTO(Set<Store> source){
        if(source.isEmpty()) return null;

        Set<StoreDTO> result = new HashSet<>();
        for(Store s : source) result.add(convertToDTO(s));

        return result;
    }

    public Set<Store> convertToJPA(Set<StoreDTO> source){
        if(source.isEmpty()) return null;

        Set<Store> result = new HashSet<>();
        for(StoreDTO s : source) result.add(convertToJPA(s));

        return result;
    }
}
