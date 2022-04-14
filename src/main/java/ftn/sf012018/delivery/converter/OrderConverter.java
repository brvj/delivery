package ftn.sf012018.delivery.converter;

import ftn.sf012018.delivery.converter.user.CustomerConverter;
import ftn.sf012018.delivery.model.dto.OrderDTO;
import ftn.sf012018.delivery.model.mappings.Item;
import ftn.sf012018.delivery.model.mappings.Order;
import ftn.sf012018.delivery.repository.ItemRepository;
import ftn.sf012018.delivery.repository.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OrderConverter {
    @Autowired
    CustomerConverter customerConverter;

    @Autowired
    ItemConverter itemConverter;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ItemRepository itemRepository;

    public OrderDTO convertToDTO(Order source){
        if(source == null) return null;

        return new OrderDTO(source.getId(), source.getOrderDate(), source.isDelivered(), source.getRating(),
                source.getComment(), source.isAnonymousComment(), source.isArchivedComment(),
                customerConverter.convertToDTO(source.getCustomer()), itemConverter.convertToDTO(source.getItems()));
    }

    public Order convertToJPA(OrderDTO source){
        if(source == null) return null;

        if(source.getCustomerDTO() == null || source.getItemDTOS().isEmpty() ||
                !customerRepository.existsById(source.getCustomerDTO().getId()))
            throw new IllegalArgumentException();

        Order order = new Order();

        order.setId(source.getId());
        order.setOrderDate(source.getOrderDate());
        order.setRating(source.getRating());
        order.setComment(source.getComment());
        order.setDelivered(source.isDelivered());
        order.setAnonymousComment(source.isAnonymousComment());
        order.setArchivedComment(source.isArchivedComment());
        order.setCustomer(customerRepository.findCustomerByOrder_Id(source.getId()).get());
        order.setItems((Set<Item>) itemRepository.findItemsByOrder_Id(source.getId(), Pageable.unpaged()));

        return order;
    }

    public Set<OrderDTO> convertToDTO(Set<Order> source){
        if(source.isEmpty()) return null;

        Set<OrderDTO> result = new HashSet<>();
        for(Order o : source) result.add(convertToDTO(o));

        return result;
    }

    public Set<Order> convertToJPA(Set<OrderDTO> source){
        if(source.isEmpty()) return null;

        Set<Order> result = new HashSet<>();
        for (OrderDTO o : source) result.add(convertToJPA(o));

        return result;
    }
}
