package jpabook.jointable.manytomany;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Parent13 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT13_ID")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(
        name = "PARENT13_CHILD13",
        joinColumns = @JoinColumn(name = "PARENT13_ID"),
        inverseJoinColumns = @JoinColumn(name = "CHILD13_ID")
    )
    private List<Child13> child = new ArrayList<>();
}
