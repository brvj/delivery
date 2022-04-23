package ftn.sf012018.delivery.model.query;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderQueryOptions {
    private String comment;
    private int ratingStart;
    private int ratingEnd;
    private double priceStart;
    private double priceEnd;
    private boolean delivered;
    private CustomerDTO customerDTO;
}
