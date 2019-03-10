package jpabook.complexkey.nonidentifying.embedded;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import jpabook.complexkey.nonidentifying.idclass.Parent05;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Child06 {

    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT06_ID1", referencedColumnName = "PARENT06_ID1"),
        @JoinColumn(name = "PARENT06_ID2", referencedColumnName = "PARENT06_ID2")
    })
    private Parent06 parent;
}
