package ftn.sf012018.delivery.model.jpa.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Admin extends User{
}
