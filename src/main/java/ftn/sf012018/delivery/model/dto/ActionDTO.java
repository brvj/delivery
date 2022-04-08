package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
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
public class ActionDTO {
    private Long id;
    private int percentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String text;
    private StoreDTO sstoreDTO;
    private Set<ArticleDTO> articleDTOS;
}
