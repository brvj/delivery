package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.ActionDTO;
import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actions")
public class ActionController {
    @Autowired
    private ActionService actionService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> index(@RequestBody ActionDTO actionDTO){
        try {
            actionService.index(actionDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<ActionDTO>> getActions(@RequestBody StoreDTO storeDTO, Pageable pageable){
        try {
            return new ResponseEntity<>(actionService.getByStoreAndCurrentDate(storeDTO, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ActionDTO> getById(@PathVariable("id") String id){
        ActionDTO response = actionService.getById(id);

        if (response != null) return new ResponseEntity<>(response, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
