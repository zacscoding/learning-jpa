package jpabook.jointable.manytoone;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Parent12 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT12_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Child12> child = new ArrayList<>();

}
