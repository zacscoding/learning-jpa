package jpabook.orphan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Child03 {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Parent03 parent;
}