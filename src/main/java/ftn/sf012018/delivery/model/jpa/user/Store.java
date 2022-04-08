package ftn.sf012018.delivery.model.jpa.user;

import ftn.sf012018.delivery.model.jpa.Action;
import ftn.sf012018.delivery.model.jpa.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "store")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Store extends User{
    @Column(name = "working_since", nullable = false)
    private LocalDate workingSince;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "store")
    private Set<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "store")
    private Set<Action> actions;
}
