package ftn.sf012018.delivery.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class StoreDTO extends UserDTO{
    private LocalDate workingSince;
    private String email;
    private String address;
    private String name;

    public StoreDTO(Long id, String firstname, String lastname, String username, String password, boolean blocked,
                         LocalDate workingSince, String email, String address, String name) {
        super(id, firstname, lastname, username, password, blocked);
        this.workingSince = workingSince;
        this.email = email;
        this.address = address;
        this.name = name;
    }
}
