package jpabook.complexkey.identifying.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
public class Parent07 {

    @Id
    @Column(name = "PARENT07_ID")
    private String id;

    private String name;
}
