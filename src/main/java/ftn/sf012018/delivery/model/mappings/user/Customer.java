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
@Document(indexName = "customers")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Customer extends User{
    @Field(type = FieldType.Keyword)
    private String address;

    @Builder
    public Customer(Long id, String firstname, String lastname, String username, String password, boolean blocked,
                       String address){
        super(id, firstname, lastname, username, password, blocked);
        this.address = address;
    }
}