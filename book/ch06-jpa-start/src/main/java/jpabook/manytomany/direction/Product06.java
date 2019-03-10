package jpabook.manytomany.direction;

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
public class Product06 {

    @Id
    @Column(name = "PRODUCT06_ID")
    private String id;

    private String name;
}
