package ftn.sf012018.delivery.service.user;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;

public interface IAdminService {

    AdminDTO index(Admin admin);

    AdminDTO findByUsernameAndPassword(String userName, String password);
}
