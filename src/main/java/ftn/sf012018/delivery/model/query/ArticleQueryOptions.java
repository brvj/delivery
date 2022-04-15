package ftn.sf012018.delivery.model.query;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleQueryOptions {
    private String name;
    private String description;
    private double priceStart;
    private double priceEnd;
    private int ratingStart;
    private int ratingEnd;
    private int commentSumStart;
    private int commentSumEnd;
    private StoreDTO storeDTO;
}
