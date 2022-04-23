package ftn.sf012018.delivery.service;

import ftn.sf012018.delivery.model.dto.OrderDTO;
import ftn.sf012018.delivery.model.query.OrderQueryOptions;

import java.util.Set;

public interface IOrderService {
    void index (OrderDTO orderDTO);

    Set<OrderDTO> getByCustomQuery (OrderQueryOptions orderQueryOptions);

    void commentAndRate (OrderDTO orderDTO);
}
