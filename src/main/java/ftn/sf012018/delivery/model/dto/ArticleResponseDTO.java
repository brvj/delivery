package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArticleResponseDTO {
    private String id;
    private String name;
    private String description;
    private double price;
    private String image;
    private StoreDTO storeDTO;
}
