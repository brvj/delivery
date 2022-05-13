package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.service.impl.user.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/stores")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Set<StoreDTO>> getAll(Pageable pageable){
        Set<StoreDTO> stores = storeService.getAll(pageable);

        if (!stores.isEmpty()) return new ResponseEntity<>(stores, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
