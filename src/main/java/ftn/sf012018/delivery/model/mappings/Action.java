package ftn.sf012018.delivery.model.mappings;

import ftn.sf012018.delivery.model.mappings.user.Store;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(indexName = "actions")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Action {
    @Id
    private Long id;

    @Field(type = FieldType.Integer)
    private int percentage;

    @Field(type = FieldType.Date)
    private LocalDate startDate;

    @Field(type = FieldType.Date)
    private LocalDate endDate;

    @Field(type = FieldType.Text)
    private String text;

    @Field(type = FieldType.Nested)
    private Set<Article> articles;

    @Field(type = FieldType.Object)
    private Store store;
}
