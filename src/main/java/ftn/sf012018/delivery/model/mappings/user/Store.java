package ftn.sf012018.delivery.model.mappings.user;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(indexName = "stores")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Store extends User{
    @Field(type = FieldType.Date)
    private LocalDate workingSince;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Keyword)
    private String address;

    @Field(type = FieldType.Keyword)
    private String name;
}
