package ftn.sf012018.delivery.model.mappings.user;

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
@Document(indexName = "users")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public abstract class User {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String firstname;

    @Field(type = FieldType.Keyword)
    private String lastname;

    @Field(type = FieldType.Keyword)
    private String username;

    @Field(type = FieldType.Keyword)
    private String password;

    @Field(type = FieldType.Boolean)
    private boolean blocked;
}
