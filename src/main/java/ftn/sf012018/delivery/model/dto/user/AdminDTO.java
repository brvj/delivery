package ftn.sf012018.delivery.model.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdminDTO extends UserDTO{
    @Builder
    public AdminDTO(String id, String firstname, String lastname, String username, String password, boolean blocked) {
        super(id, firstname, lastname, username, password, blocked);
    }
}
