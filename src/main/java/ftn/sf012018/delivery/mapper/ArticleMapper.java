package ftn.sf012018.delivery.mapper;

import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ArticleDTO;
import ftn.sf012018.delivery.model.mappings.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ArticleMapper {
    @Autowired
    StoreMapper storeMapper;

    public ArticleDTO mapToDTO(Article source){
        if(source == null) return null;

        return ArticleDTO.builder()
                .id(source.getId())
                .name(source.getName())
               // .description()
                .price(source.getPrice())
                .image(source.getImage())
                .storeDTO(storeMapper.mapToDTO(source.getStore()))
                .build();
    }

    public Article mapModel(ArticleDTO source){
        if(source == null) return null;

        return Article.builder()
                .id(source.getId())
                .name(source.getName())
                // .description()
                .price(source.getPrice())
                .image(source.getImage())
                .store(storeMapper.mapModel(source.getStoreDTO()))
                .build();
    }

    public Set<ArticleDTO> mapToDTO(Set<Article> source){
        if(source.isEmpty()) return null;

        Set<ArticleDTO> result = new HashSet<>();
        for(Article a : source) result.add(mapToDTO(a));

        return result;
    }

    public Set<Article> mapModel(Set<ArticleDTO> source){
        if(source.isEmpty()) return null;

        Set<Article> result = new HashSet<>();
        for(ArticleDTO a : source) result.add(mapModel(a));

        return result;
    }
}
