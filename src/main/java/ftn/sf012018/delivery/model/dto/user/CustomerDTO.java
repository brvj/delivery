package ftn.sf012018.delivery.model.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO extends UserDTO{
    private String address;

    public CustomerDTO(Long id, String firstname, String lastname, String username, String password, boolean blocked,
                       String address){
        super(id, firstname, lastname, username, password, blocked);
        this.address = address;
    }
}
