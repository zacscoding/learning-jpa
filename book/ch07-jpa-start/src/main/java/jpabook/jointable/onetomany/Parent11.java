package jpabook.jointable.onetomany;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Parent11 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT11_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(
        name = "PARENT11_CHILD11",
        joinColumns = @JoinColumn(name = "PARENT11_ID"),
        inverseJoinColumns = @JoinColumn(name = "CHILD11_ID")
    )
    private List<Child11> child = new ArrayList<>();
}
