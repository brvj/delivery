package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OrderDTO {
    private Long id;
    private LocalDate orderDate;
    private boolean delivered;
    private int rating;
    private String comment;
    private boolean anonymousComment;
    private boolean archivedComment;
    private CustomerDTO customerDTO;
    private Set<ItemDTO> itemDTOS;
}
