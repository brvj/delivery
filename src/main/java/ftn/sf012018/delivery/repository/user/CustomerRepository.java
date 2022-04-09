package ftn.sf012018.delivery.repository.user;

import ftn.sf012018.delivery.model.jpa.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c left join c.orders orders where orders.id = ?1")
    Optional<Customer> findCustomerByOrder_Id(Long id);
}