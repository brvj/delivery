package ftn.sf012018.delivery.converter;

import ftn.sf012018.delivery.model.dto.ItemDTO;
import ftn.sf012018.delivery.model.mappings.Item;
import ftn.sf012018.delivery.repository.ArticleRepository;
import ftn.sf012018.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ItemConverter {
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ArticleConverter articleConverter;

    public ItemDTO convertToDTO(Item source){
        if(source == null) return null;

        return new ItemDTO(source.getId(), source.getQuantity(), articleConverter.convertToDTO(source.getArticle()));
    }

    public Item convertToJPA(ItemDTO source){
        if(source == null) return null;

        if(source.getArticleDTO() == null || !articleRepository.existsById(source.getArticleDTO().getId()))
            throw new IllegalArgumentException();

        Item item = new Item();

        item.setId(source.getId());
        item.setQuantity(source.getQuantity());
        item.setOrder(orderRepository.findOrderByItem_Id(source.getId()).get());
        item.setArticle(articleRepository.findArticleByItem_Id(source.getId()).get());

        return item;
    }

    public Set<ItemDTO> convertToDTO(Set<Item> source){
        if(source.isEmpty()) return null;

        Set<ItemDTO> result = new HashSet<>();
        for(Item i : source) result.add(convertToDTO(i));

        return result;
    }

    public Set<Item> convertToJPA(Set<ItemDTO> source){
        if(source.isEmpty()) return null;

        Set<Item> result = new HashSet<>();
        for(ItemDTO i : source) result.add(convertToJPA(i));

        return result;
    }
}
