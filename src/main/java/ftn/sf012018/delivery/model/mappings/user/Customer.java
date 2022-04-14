package ftn.sf012018.delivery.model.mappings.user;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(indexName = "customers")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Customer extends User{
    @Field(type = FieldType.Keyword)
    private String address;
}