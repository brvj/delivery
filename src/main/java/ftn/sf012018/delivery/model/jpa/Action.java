package ftn.sf012018.delivery.model.jpa;

import ftn.sf012018.delivery.model.jpa.user.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "action")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id", unique = true, nullable = false)
    private Long id;

    @Column(name = "percentage", nullable = false)
    private int percentage;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToMany(targetEntity = Article.class)
    private Set<Article> articles;

    @ManyToOne
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    private Store store;
}
