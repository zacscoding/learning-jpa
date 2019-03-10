package jpabook.embedded;

import javax.persistence.Embedded;
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
public class Member02 {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Embedded
    Period02 workPeriod;

    @Embedded
    Address02 homeAddress;

}
