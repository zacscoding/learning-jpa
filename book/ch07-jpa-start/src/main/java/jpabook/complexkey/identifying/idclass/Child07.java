package jpabook.complexkey.identifying.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"parent", "childId"})
@Entity
@IdClass(Child07Id.class)
public class Child07 {

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT07_ID")
    private Parent07 parent;

    @Id
    @Column(name = "CHILD_ID")
    private String childId;

    private String name;
}
