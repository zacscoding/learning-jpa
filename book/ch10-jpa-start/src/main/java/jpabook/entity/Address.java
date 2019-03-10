package jpabook.entity;

import javax.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"city", "street", "zipcode"})
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
