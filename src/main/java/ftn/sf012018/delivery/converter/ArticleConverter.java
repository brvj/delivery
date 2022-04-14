package ftn.sf012018.delivery.converter;

import ftn.sf012018.delivery.converter.user.StoreConverter;
import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.mappings.Action;
import ftn.sf012018.delivery.model.mappings.Article;
import ftn.sf012018.delivery.model.mappings.Item;
import ftn.sf012018.delivery.repository.ActionRepository;
import ftn.sf012018.delivery.repository.ItemRepository;
import ftn.sf012018.delivery.repository.user.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ArticleConverter {
    @Autowired
    StoreConverter storeConverter;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    StoreRepository storeRepository;

    public ArticleDTO convertToDTO(Article source){
        if(source == null) return null;

        return new ArticleDTO(source.getId(), source.getName(), source.getDescription(), source.getPrice(),
                source.getImagePath(), storeConverter.convertToDTO(source.getStore()));
    }

    public Article convertToJPA(ArticleDTO source){
        if(source == null) return null;

        if(source.getStoreDTO() == null || !storeRepository.existsById(source.getStoreDTO().getId()))
            throw new IllegalArgumentException();

        Article article = new Article();

        article.setId(source.getId());
        article.setName(source.getName());
        article.setDescription(source.getDescription());
        article.setPrice(source.getPrice());
        article.setImagePath(source.getImagePath());
        article.setStore(storeRepository.findStoreByArticle_Id(source.getId()).get());
        article.setItems((Set<Item>) itemRepository.findItemsByArticle_Id(source.getId(), Pageable.unpaged()));
        article.setActions((Set<Action>) actionRepository.findActionsByArticle_Id(source.getId(), Pageable.unpaged()));

        return article;
    }

    public Set<ArticleDTO> convertToDTO(Set<Article> source){
        if(source.isEmpty()) return null;

        Set<ArticleDTO> result = new HashSet<>();
        for(Article a : source) result.add(convertToDTO(a));

        return result;
    }

    public Set<Article> convertToJPA(Set<ArticleDTO> source){
        if(source.isEmpty()) return null;

        Set<Article> result = new HashSet<>();
        for(ArticleDTO a : source) result.add(convertToJPA(a));

        return result;
    }
}
