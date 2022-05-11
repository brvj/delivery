package ftn.sf012018.delivery.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STORE')")
public @interface AuthorizeAdminOrStore {
}
