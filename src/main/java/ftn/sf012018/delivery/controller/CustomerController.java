package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.service.impl.user.CustomerService;
import ftn.sf012018.delivery.service.impl.user.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private StoreService storeService;

    @PutMapping(value = "/block", consumes = "application/json")
    public void block(@RequestBody CustomerDTO customerDTO){
        customerService.block(customerDTO.getId());
    }

    @GetMapping(value = "/unblocked", consumes = "application/json")
    public Set<CustomerDTO> getUnblockedCustomers(){
        return customerService.getAllUnblockedCustomers(Boolean.FALSE, PageRequest.of(0,10));
    }

    @GetMapping(value = "/blocked", consumes = "application/json")
    public Set<CustomerDTO> getBlockedCustomers(){
        return customerService.getAllBlockedCustomers(true, Pageable.unpaged());
    }

}
