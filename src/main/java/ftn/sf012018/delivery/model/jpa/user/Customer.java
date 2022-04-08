package ftn.sf012018.delivery.model.jpa.user;

import ftn.sf012018.delivery.model.jpa.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer extends User{
    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<Order> orders;
}