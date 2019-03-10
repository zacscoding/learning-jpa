package jpabook.complexkey.nonidentifying.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@IdClass(Parent05Id.class)
@ToString
public class Parent05 {

    @Id
    @Column(name = "PARENT05_ID1")
    private String id1; // Parent05Id.id1 과 연결

    @Id
    @Column(name = "PARENT05_ID2")
    private String id2;

    private String name;
}
