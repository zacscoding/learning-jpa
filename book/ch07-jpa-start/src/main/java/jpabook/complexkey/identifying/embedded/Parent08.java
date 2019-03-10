package jpabook.complexkey.identifying.embedded;

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
public class Parent08 {

    @Id
    @Column(name = "PARENT08_ID")
    private String id;

    private String name;
}
