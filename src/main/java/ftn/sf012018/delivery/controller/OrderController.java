package ftn.sf012018.delivery.controller;

import ftn.sf012018.delivery.model.dto.OrderDTO;
import ftn.sf012018.delivery.model.query.OrderQueryOptions;
import ftn.sf012018.delivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Void> index(@RequestBody OrderDTO orderDTO){
        try {
            orderService.index(orderDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<Set<OrderDTO>> getByQuery(@RequestBody OrderQueryOptions orderQueryOptions){
        try {
            return new ResponseEntity<>(orderService.getByCustomQuery(orderQueryOptions), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/comment", consumes = "application/json")
    public ResponseEntity<String> comment (@RequestBody OrderDTO orderDTO){
        try {
            orderService.commentAndRate(orderDTO);

            return new ResponseEntity<>(orderDTO.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/set-delivery-status", consumes = "application/json")
    public ResponseEntity<String> setDeliveryStatus(@RequestBody OrderDTO orderDTO){
        try {
            orderService.setOrderDelivered(orderDTO);

            return new ResponseEntity<>(orderDTO.getId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<OrderDTO> getById(@PathVariable("id") String id){
        OrderDTO response = orderService.getById(id);

        if (response != null) return new ResponseEntity<>(response, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
