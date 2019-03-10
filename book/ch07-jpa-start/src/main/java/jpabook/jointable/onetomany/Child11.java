package jpabook.jointable.onetomany;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * https://github.com/holyeye/jpabook
 */
@Entity
public class Child11 {

    @Id
    @GeneratedValue
    @Column(name = "CHILD11_ID")
    private Long id;

    private String name;
}
