package ftn.sf012018.delivery.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemDTO {
    private Long id;
    private int quantity;
    private ArticleDTO articleDTO;
}
