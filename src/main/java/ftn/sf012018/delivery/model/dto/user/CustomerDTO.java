package ftn.sf012018.delivery.model.dto.user;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO extends UserDTO{
    private String address;

    @Builder
    public CustomerDTO(Long id, String firstname, String lastname, String username, String password, boolean blocked,
                       String address){
        super(id, firstname, lastname, username, password, blocked);
        this.address = address;
    }
}
