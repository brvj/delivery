package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
