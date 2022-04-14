package ftn.sf012018.delivery.converter.user;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;
import ftn.sf012018.delivery.repository.user.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminConverter {
    @Autowired
    AdminRepository adminRepository;

    public AdminDTO convertToDTO(Admin source){
        if(source == null) return null;

        return new AdminDTO(source.getId(), source.getFirstname(), source.getLastname(), source.getUsername(),
                source.getPassword(), source.isBlocked());
    }
    public Admin convertToJPA(AdminDTO source){
        if(source == null) return null;

        Admin admin = new Admin();

        admin.setId(source.getId());
        admin.setFirstname(source.getFirstname());
        admin.setLastname(source.getLastname());
        admin.setUsername(source.getUsername());
        admin.setPassword(source.getPassword());
        admin.setBlocked(source.isBlocked());

        return admin;
    }

    public Set<AdminDTO> convertToDTO(Set<Admin> source){
        if(source.isEmpty()) return null;

        Set<AdminDTO> result = new HashSet<>();
        for(Admin a : source) result.add((convertToDTO(a)));

        return result;
    }

    public Set<Admin> convertToJPA(Set<AdminDTO> source){
        if(source.isEmpty()) return null;

        Set<Admin> result = new HashSet<>();
        for(AdminDTO a : source) result.add(convertToJPA(a));

        return result;
    }
}
