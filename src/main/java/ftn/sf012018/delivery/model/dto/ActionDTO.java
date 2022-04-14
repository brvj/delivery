package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ActionDTO {
    private Long id;
    private int percentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String text;
    private StoreDTO storeDTO;
    private Set<ArticleDTO> articleDTOS;
}
