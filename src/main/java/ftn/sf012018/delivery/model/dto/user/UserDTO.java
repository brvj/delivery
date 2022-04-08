package ftn.sf012018.delivery.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class UserDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean blocked;
}
