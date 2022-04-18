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

    @Builder
    public Store(String id, String firstname, String lastname, String username, String password, boolean blocked,
                    LocalDate workingSince, String email, String address, String name) {
        super(id, firstname, lastname, username, password, blocked);
        this.workingSince = workingSince;
        this.email = email;
        this.address = address;
        this.name = name;
    }
}
