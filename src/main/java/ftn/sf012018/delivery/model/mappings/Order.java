package ftn.sf012018.delivery.model.mappings;

import ftn.sf012018.delivery.model.mappings.user.Customer;
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
@Document(indexName = "orders")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Order {
    @Id
    private String id;

    @Field(type = FieldType.Date)
    private LocalDate orderDate;

    @Field(type = FieldType.Boolean)
    private boolean delivered;

    @Field(type = FieldType.Integer)
    private int rating;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Boolean)
    private boolean anonymousComment;

    @Field(type = FieldType.Boolean)
    private boolean archivedComment;

    @Field(type = FieldType.Object)
    private Customer customer;

    @Field(type = FieldType.Nested)
    private Set<Item> items;
}
