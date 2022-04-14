package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ArticleDTO {
    private Long id;
    private String name;
    private MultipartFile description;
    private double price;
    private byte image;
    private StoreDTO storeDTO;
}
