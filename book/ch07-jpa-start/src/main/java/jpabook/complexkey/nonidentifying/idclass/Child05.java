package jpabook.complexkey.nonidentifying.idclass;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

/**
 * https://github.com/holyeye/jpabook
 */
@Entity
public class Child05 {

    @Id
    private String id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT05_ID1", referencedColumnName = "PARENT05_ID1"),
        @JoinColumn(name = "PARENT05_ID2", referencedColumnName = "PARENT05_ID2")
    })
    private Parent05 parent;
}
