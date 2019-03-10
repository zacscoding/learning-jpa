package jpabook.jointable.manytomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Child13 {

    @Id
    @GeneratedValue
    @Column(name = "CHILD13_ID")
    private Long id;

    private String name;
}
