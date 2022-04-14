package ftn.sf012018.delivery.mapper.user;

import ftn.sf012018.delivery.model.dto.user.StoreDTO;
import ftn.sf012018.delivery.model.mappings.user.Store;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StoreMapper {

    public StoreDTO mapToDTO(Store source){
        if(source == null) return null;

        return StoreDTO.builder()
                .id(source.getId())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .username(source.getUsername())
                .password(source.getPassword())
                .blocked(source.isBlocked())
                .workingSince(source.getWorkingSince())
                .email(source.getEmail())
                .address(source.getAddress())
                .name(source.getName())
                .build();
    }

    public Store mapModel(StoreDTO source){
        if(source == null) return null;

        return Store.builder()
                .id(source.getId())
                .firstname(source.getFirstname())
                .lastname(source.getLastname())
                .username(source.getUsername())
                .password(source.getPassword())
                .blocked(source.isBlocked())
                .workingSince(source.getWorkingSince())
                .email(source.getEmail())
                .address(source.getAddress())
                .name(source.getName())
                .build();
    }

    public Set<StoreDTO> mapToDTO(Set<Store> source){
        if(source.isEmpty()) return null;

        Set<StoreDTO> result = new HashSet<>();
        for(Store s : source) result.add(mapToDTO(s));

        return result;
    }

    public Set<Store> mapModel(Set<StoreDTO> source){
        if(source.isEmpty()) return null;

        Set<Store> result = new HashSet<>();
        for(StoreDTO s : source) result.add(mapModel(s));

        return result;
    }
}
