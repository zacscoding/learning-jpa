package jpabook.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Embeddable
public class Address02 {

    @Column(name = "city")
    private String city;

    private String street;

    private String zipcode;
}
