package ftn.sf012018.delivery.model.dto.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class UserDTO {
    private String id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private boolean blocked;
}
