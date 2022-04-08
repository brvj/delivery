package ftn.sf012018.delivery.model.dto;

import ftn.sf012018.delivery.model.jpa.user.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ArticleDTO {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imagePath;
    private Store storeDTO;
}
