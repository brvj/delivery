package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import ftn.sf012018.delivery.model.dto.user.PasswordUpdateDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.service.user.DefaultUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/users")
public class DefaultUserController {
    @Autowired
    private DefaultUserService defaultUserService;

    @PostMapping(value = "/index-admin", consumes = "application/json")
    public ResponseEntity<Void> indexAdmin(@RequestBody AdminDTO adminDTO){
        try{
            defaultUserService.indexAdmin(adminDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/index-customer", consumes = "application/json")
    public ResponseEntity<Void> indexCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            defaultUserService.indexCustomer(customerDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/index-store", consumes = "application/json")
    public ResponseEntity<Void> indexStore(@RequestBody StoreDTO storeDTO){
        try {
            defaultUserService.indexStore(storeDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/change-customer-password", consumes = "application/json")
    public ResponseEntity<String> changeCustomerPassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO){
        try {
            defaultUserService.changeCustomerPassword(passwordUpdateDTO);

            return new ResponseEntity<>(passwordUpdateDTO.getUserId(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/change-store-password", consumes = "application/json")
    public ResponseEntity<String> changeStorePassword(@RequestBody PasswordUpdateDTO passwordUpdateDTO){
        try {
            defaultUserService.changeStorePassword(passwordUpdateDTO);

            return new ResponseEntity<>(passwordUpdateDTO.getUserId(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update-customer", consumes = "application/json")
    public ResponseEntity<String> updateCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            defaultUserService.updateCustomer(customerDTO);

            return new ResponseEntity<>(customerDTO.getId(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update-store", consumes = "application/json")
    public ResponseEntity<String> updateStore(@RequestBody StoreDTO storeDTO){
        try {
            defaultUserService.updateStore(storeDTO);

            return new ResponseEntity<>(storeDTO.getId(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
