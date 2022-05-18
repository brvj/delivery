package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.service.user.CustomerService;
import ftn.sf012018.delivery.service.user.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<CustomerDTO>> getUnblockedCustomers(Pageable pageable){
        Page<CustomerDTO> customers = customerService.getAllUnblockedCustomers(Boolean.FALSE, pageable);

        if (!customers.isEmpty()) return new ResponseEntity<>(customers, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/blocked", produces = "application/json")
    public ResponseEntity<Page<CustomerDTO>> getBlockedCustomers(Pageable pageable){
        Page<CustomerDTO> customers = customerService.getAllBlockedCustomers(true, pageable);

        if (!customers.isEmpty()) return new ResponseEntity<>(customers, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<CustomerDTO> getById(@PathVariable("id") String id){
        CustomerDTO response = customerService.getById(id);

        if (response != null) return new ResponseEntity<>(response, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
