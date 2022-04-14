package ftn.sf012018.delivery.mapper.user;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.mappings.user.Customer;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CustomerMapper {
    public CustomerDTO mapToDTO(Customer source){
        if(source == null) return null;

        return CustomerDTO.builder()
                .id(source.getId())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .username(source.getUsername())
                .password(source.getPassword())
                .blocked(source.isBlocked())
                .address(source.getAddress())
                .build();
    }

    public Customer mapModel(CustomerDTO source){
        if(source == null) return null;

        return Customer.builder()
                .id(source.getId())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .username(source.getUsername())
                .password(source.getPassword())
                .blocked(source.isBlocked())
                .address(source.getAddress())
                .build();
    }

    public Set<CustomerDTO> mapToDTO(Set<Customer> source){
        if(source.isEmpty()) return null;

        Set<CustomerDTO> result = new HashSet<>();
        for(Customer c : source) result.add(mapToDTO(c));

        return result;
    }

    public Set<Customer> mapModel(Set<CustomerDTO> source){
        if(source.isEmpty()) return null;

        Set<Customer> result = new HashSet<>();
        for(CustomerDTO c : source) result.add(mapModel(c));

        return result;
    }
}
