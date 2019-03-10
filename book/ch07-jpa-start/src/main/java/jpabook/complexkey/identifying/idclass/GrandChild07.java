package jpabook.complexkey.identifying.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@IdClass(GrandChild07Id.class)
public class GrandChild07 {

    @Id
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT07_ID"),
        @JoinColumn(name = "CHILD_ID")
    })
    private Child07 child;

    @Id
    @Column(name = "GRANDCHILD07_ID")
    private String id;

    private String name;
}
