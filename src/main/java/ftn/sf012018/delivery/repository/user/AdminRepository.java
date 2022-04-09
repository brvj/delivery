package ftn.sf012018.delivery.repository.user;

import ftn.sf012018.delivery.model.jpa.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}