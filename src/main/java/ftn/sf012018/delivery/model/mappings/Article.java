package ftn.sf012018.delivery.model.mappings;

import ftn.sf012018.delivery.model.mappings.user.Store;
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
@Document(indexName = "articles")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Article {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Text)
    private String imagePath;

    @Field(type = FieldType.Object)
    private Store store;

    @Field(type = FieldType.Keyword)
    private String keywords;

    private String filename;
}
