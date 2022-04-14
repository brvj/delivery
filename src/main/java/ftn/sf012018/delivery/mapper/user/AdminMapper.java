package ftn.sf012018.delivery.mapper.user;

import ftn.sf012018.delivery.model.dto.user.AdminDTO;
import ftn.sf012018.delivery.model.mappings.user.Admin;
import ftn.sf012018.delivery.repository.user.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminMapper {
    public AdminDTO mapToDTO(Admin source){
        if(source == null) return null;

        return AdminDTO.builder()
                .id(source.getId())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .username(source.getUsername())
                .password(source.getPassword())
                .blocked(source.isBlocked())
                .build();
    }
    public Admin mapModel(AdminDTO source){
        if(source == null) return null;

        return (Admin) Admin.builder()
                .id(source.getId())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .username(source.getUsername())
                .password(source.getPassword())
                .blocked(source.isBlocked())
                .build();
    }

    public Set<AdminDTO> mapToDTO(Set<Admin> source){
        if(source.isEmpty()) return null;

        Set<AdminDTO> result = new HashSet<>();
        for(Admin a : source) result.add((mapToDTO(a)));

        return result;
    }

    public Set<Admin> mapModel(Set<AdminDTO> source){
        if(source.isEmpty()) return null;

        Set<Admin> result = new HashSet<>();
        for(AdminDTO a : source) result.add(mapModel(a));

        return result;
    }
}
