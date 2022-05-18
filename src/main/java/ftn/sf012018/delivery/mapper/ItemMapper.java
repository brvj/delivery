package ftn.sf012018.delivery.mapper;

import ftn.sf012018.delivery.model.dto.ArticleResponseDTO;
import ftn.sf012018.delivery.model.dto.ItemDTO;
import ftn.sf012018.delivery.model.mappings.Item;
import ftn.sf012018.delivery.contract.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ItemMapper {
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleRepository articleRepository;

    public ItemDTO mapToDTO(Item source){
        if(source == null) return null;

        return ItemDTO.builder()
                .id(source.getId())
                .quantity(source.getQuantity())
                .articleDTO(articleMapper.mapToDTO(source.getArticle()))
                .build();
    }

    public Item mapModel(ItemDTO source){
        if(source == null) return null;

        ArticleResponseDTO article = articleMapper.mapToDTO(articleRepository.findById(source.getArticleDTO().getId()).get());

        return Item.builder()
                .id(source.getId())
                .quantity(source.getQuantity())
                .article(articleMapper.mapModel(article))
                .build();
    }

    public Set<ItemDTO> mapToDTO(Set<Item> source){
        if(source.isEmpty()) return null;

        Set<ItemDTO> result = new HashSet<>();
        for(Item i : source) result.add(mapToDTO(i));

        return result;
    }

    public Set<Item> mapModel(Set<ItemDTO> source){
        if(source.isEmpty()) return null;

        Set<Item> result = new HashSet<>();
        for(ItemDTO i : source) result.add(mapModel(i));

        return result;
    }
}
