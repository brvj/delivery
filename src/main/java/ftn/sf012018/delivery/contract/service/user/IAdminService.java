package ftn.sf012018.delivery.contract.service.user;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;

public interface IAdminService {

    void index(AdminDTO admin);

    AdminDTO getByUsernameAndPassword(String username, String password);

    Admin getByUsernameAndBlocked(String username);

    AdminDTO getById(String id);
}
