package ftn.sf012018.delivery.model.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemDTO {
    private String id;
    private int quantity;
    private ArticleDTO articleDTO;
}
