package ftn.sf012018.delivery.service.user;

import ftn.sf012018.delivery.mapper.user.AdminMapper;
import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;
import ftn.sf012018.delivery.contract.repository.user.AdminRepository;
import ftn.sf012018.delivery.contract.service.user.IAdminService;
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
    public AdminDTO getByUsernameAndPassword(String username, String password) {
        if(username.equals("") || password.equals(""))
            return null;

        return adminMapper.mapToDTO(adminRepository.findByUsernameAndPassword(username, password));
    }

    @Override
    public Admin getByUsernameAndBlocked(String username) {
        return adminRepository.findByUsernameAndBlocked(username, Boolean.FALSE);
    }

    @Override
    public AdminDTO getById(String id) {
        Admin admin = adminRepository.findById(id).get();

        if(admin != null) return adminMapper.mapToDTO(admin);

        return null;
    }

}
