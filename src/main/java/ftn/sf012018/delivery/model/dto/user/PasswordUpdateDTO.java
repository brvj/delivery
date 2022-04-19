package ftn.sf012018.delivery.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDTO {
    private String userId;
    private String oldPassword;
    private String newPassword;
    private String repeatedNewPassword;
}
