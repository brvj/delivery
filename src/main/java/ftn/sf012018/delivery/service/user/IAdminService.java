package ftn.sf012018.delivery.service.user;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;

public interface IAdminService {

    void index(AdminDTO admin);

    AdminDTO findByUsernameAndPassword(String username, String password);
}
