package jpabook.manytoone.direction;

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
public class Team01 {

    @Id
    @GeneratedValue
    @Column(name = "TEAM01_ID")
    private Long id;

    private String name;

}
