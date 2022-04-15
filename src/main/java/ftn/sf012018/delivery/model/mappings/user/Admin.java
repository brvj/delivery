package ftn.sf012018.delivery.model.mappings.user;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

@AllArgsConstructor
@Getter
@Setter
@Document(indexName = "admins")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class Admin extends User{
    @Builder
    public Admin(Long id, String firstname, String lastname, String username, String password, boolean blocked) {
        super(id, firstname, lastname, username, password, blocked);
    }
}
