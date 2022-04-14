package ftn.sf012018.delivery.converter.user;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.mappings.Order;
import ftn.sf012018.delivery.model.mappings.user.Customer;
import ftn.sf012018.delivery.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomerConverter {
    @Autowired
    OrderRepository orderRepository;

    public CustomerDTO convertToDTO(Customer source){
        if(source == null) return null;

        return new CustomerDTO(source.getId(), source.getFirstname(), source.getLastname(), source.getUsername(),
                source.getPassword(), source.isBlocked(), source.getAddress());
    }

    public Customer convertToJPA(CustomerDTO source){
        if(source == null) return null;

        Customer customer = new Customer();
        customer.setId(source.getId());
        customer.setUsername(source.getUsername());
        customer.setPassword(source.getPassword());
        customer.setFirstname(source.getFirstname());
        customer.setLastname(source.getLastname());
        customer.setBlocked(source.isBlocked());
        customer.setAddress(source.getAddress());
        customer.setOrders((Set<Order>) orderRepository.findOrdersByCustomer_Id(source.getId(), Pageable.unpaged()));

        return customer;
    }

    public Set<CustomerDTO> convertToDTO(Set<Customer> source){
        if(source.isEmpty()) return null;

        Set<CustomerDTO> result = new HashSet<>();
        for(Customer c : source) result.add(convertToDTO(c));

        return result;
    }

    public Set<Customer> convertToJPA(Set<CustomerDTO> source){
        if(source.isEmpty()) return null;

        Set<Customer> result = new HashSet<>();
        for(CustomerDTO c : source) result.add(convertToJPA(c));

        return result;
    }
}
