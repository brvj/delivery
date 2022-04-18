package ftn.sf012018.delivery.model.mappings;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(indexName = "items")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Item {
    @Id
    private String id;

    @Field(type = FieldType.Integer)
    private int quantity;

    @Field(type = FieldType.Object)
    private Article article;
}