package ftn.sf012018.delivery.mapper;

import ftn.sf012018.delivery.mapper.user.StoreMapper;
import ftn.sf012018.delivery.model.dto.ArticleRequestDTO;
import ftn.sf012018.delivery.model.dto.ArticleResponseDTO;
import ftn.sf012018.delivery.model.mappings.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class ArticleMapper {
    @Autowired
    StoreMapper storeMapper;

    public ArticleResponseDTO mapToDTO(Article source){
        if(source == null) return null;

        return ArticleResponseDTO.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .image(source.getImagePath())
                .storeDTO(storeMapper.mapToDTO(source.getStore()))
                .build();
    }

    public Article mapModel(ArticleRequestDTO source) throws IOException {
        if(source == null) return null;

        return Article.builder()
                .id(source.getId())
                .name(source.getName())
                // .description()
                .price(source.getPrice())
               // .image(source.getImage().getOriginalFilename())
                .store(storeMapper.mapModel(source.getStoreDTO()))
                .build();
    }

    public Article mapModel(ArticleResponseDTO source){
        if(source == null) return null;

        return Article.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .price(source.getPrice())
                .imagePath(source.getImage())
                .store(storeMapper.mapModel(source.getStoreDTO()))
                .build();
    }

    public Set<ArticleResponseDTO> mapToDTO(Set<Article> source){
        if(source.isEmpty()) return null;

        Set<ArticleResponseDTO> result = new HashSet<>();
        for(Article a : source) result.add(mapToDTO(a));

        return result;
    }

    public Set<Article> mapModel(Set<ArticleRequestDTO> source) throws IOException {
        if(source.isEmpty()) return null;

        Set<Article> result = new HashSet<>();
        for(ArticleRequestDTO a : source) result.add(mapModel(a));

        return result;
    }

    public Set<Article> mapModelResponse(Set<ArticleResponseDTO> source) throws IOException {
        if(source.isEmpty()) return null;

        Set<Article> result = new HashSet<>();
        for(ArticleResponseDTO a : source) result.add(mapModel(a));

        return result;
    }
}
