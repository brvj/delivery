package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.service.user.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<StoreDTO>> getAll(Pageable pageable){
        Page<StoreDTO> stores = storeService.getAll(pageable);

        if (!stores.isEmpty()) return new ResponseEntity<>(stores, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<StoreDTO> getById(@PathVariable("id") String id){
        StoreDTO response = storeService.getById(id);

        if (response != null) return new ResponseEntity<>(response, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
