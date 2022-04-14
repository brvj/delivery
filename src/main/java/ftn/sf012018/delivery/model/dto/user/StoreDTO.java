package ftn.sf012018.delivery.model.dto.user;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class StoreDTO extends UserDTO{
    private LocalDate workingSince;
    private String email;
    private String address;
    private String name;

    @Builder
    public StoreDTO(Long id, String firstname, String lastname, String username, String password, boolean blocked,
                         LocalDate workingSince, String email, String address, String name) {
        super(id, firstname, lastname, username, password, blocked);
        this.workingSince = workingSince;
        this.email = email;
        this.address = address;
        this.name = name;
    }
}
