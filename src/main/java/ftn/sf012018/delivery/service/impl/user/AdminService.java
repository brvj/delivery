package ftn.sf012018.delivery.service.impl.user;

import ftn.sf012018.delivery.mapper.user.AdminMapper;
import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;
import ftn.sf012018.delivery.repository.user.AdminRepository;
import ftn.sf012018.delivery.service.user.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void index(AdminDTO admin) {
        adminRepository.save(adminMapper.mapModel(admin));
    }

    @Override
    public AdminDTO findByUsernameAndPassword(String username, String password) {
        if(username == "" || password == "")
            return null;

        return adminMapper.mapToDTO(adminRepository.findByUsernameAndPassword(username, password));
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
