package ftn.sf012018.delivery.repository.user;

import ftn.sf012018.delivery.model.mappings.user.Admin;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends ElasticsearchRepository<Admin, Long> {
    Admin findByUsernameAndPassword(String userName, String password);
}