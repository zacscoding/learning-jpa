package jpabook.onetomany.direction;

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
public class Member03 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER03_ID")
    private Long id;



}
