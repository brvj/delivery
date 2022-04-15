package ftn.sf012018.delivery.lucene.search;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleQueryElasticsearch {
    private String field;
    private String value;
}
