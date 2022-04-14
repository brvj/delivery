package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String name;
    private MultipartFile description;
    private double price;
    private byte image;
    private StoreDTO storeDTO;
}
