package ftn.sf012018.delivery.mapper;

import ftn.sf012018.delivery.mapper.user.CustomerMapper;
import ftn.sf012018.delivery.model.dto.OrderDTO;
import ftn.sf012018.delivery.model.mappings.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrderConverter {
    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    ItemMapper itemMapper;

    public OrderDTO mapToDTO(Order source){
        if(source == null) return null;

        return OrderDTO.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .delivered(source.isDelivered())
                .rating(source.getRating())
                .comment(source.getComment())
                .anonymousComment(source.isAnonymousComment())
                .archivedComment(source.isArchivedComment())
                .customerDTO(customerMapper.mapToDTO(source.getCustomer()))
                .itemDTOS(itemMapper.mapToDTO(source.getItems()))
                .build();
    }

    public Order mapModel(OrderDTO source){
        if(source == null) return null;

        return Order.builder()
                .id(source.getId())
                .orderDate(source.getOrderDate())
                .delivered(source.isDelivered())
                .rating(source.getRating())
                .comment(source.getComment())
                .anonymousComment(source.isAnonymousComment())
                .archivedComment(source.isArchivedComment())
                .customer(customerMapper.mapModel(source.getCustomerDTO()))
                .items(itemMapper.mapModel(source.getItemDTOS()))
                .build();
    }

    public Set<OrderDTO> mapToDTO(Set<Order> source){
        if(source.isEmpty()) return null;

        Set<OrderDTO> result = new HashSet<>();
        for(Order o : source) result.add(mapToDTO(o));

        return result;
    }

    public Set<Order> mapModel(Set<OrderDTO> source){
        if(source.isEmpty()) return null;

        Set<Order> result = new HashSet<>();
        for (OrderDTO o : source) result.add(mapModel(o));

        return result;
    }
}
