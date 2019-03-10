package jpabook.jointable.manytoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Child12 {

    @Id
    @GeneratedValue
    @Column(name = "CHILD12_ID")
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinTable(
        name = "PARENT12_CHILD12",
        joinColumns = @JoinColumn(name = "CHILD12_ID"),
        inverseJoinColumns = @JoinColumn(name = "PARENT12_ID")
    )
    private Parent12 parent;
}
