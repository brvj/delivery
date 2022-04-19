package ftn.sf012018.delivery.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalHolder {
    public CustomPrincipal getCurrentPrincipal(){
        return (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
