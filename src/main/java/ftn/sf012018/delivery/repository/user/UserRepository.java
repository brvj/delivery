package ftn.sf012018.delivery.repository.user;

import ftn.sf012018.delivery.model.mappings.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}