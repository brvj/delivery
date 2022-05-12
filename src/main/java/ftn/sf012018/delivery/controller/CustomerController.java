package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.service.impl.user.CustomerService;
import ftn.sf012018.delivery.service.impl.user.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private StoreService storeService;

    @PutMapping(value = "/block", consumes = "application/json")
    public ResponseEntity<Void> block(@RequestBody CustomerDTO customerDTO){
        try {
            customerService.block(customerDTO.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/unblocked", produces = "application/json")
    public ResponseEntity<Set<CustomerDTO>> getUnblockedCustomers(){
        Set<CustomerDTO> customers = customerService.getAllUnblockedCustomers(Boolean.FALSE, PageRequest.of(0,10));

        if (!customers.isEmpty()) return new ResponseEntity<>(customers, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/blocked", produces = "application/json")
    public ResponseEntity<Set<CustomerDTO>> getBlockedCustomers(){
        Set<CustomerDTO> customers = customerService.getAllBlockedCustomers(true, Pageable.unpaged());

        if (!customers.isEmpty()) return new ResponseEntity<>(customers, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
