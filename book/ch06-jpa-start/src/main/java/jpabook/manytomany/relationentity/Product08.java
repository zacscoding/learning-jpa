package jpabook.manytomany.relationentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Product08 {

    @Id
    @Column(name = "PRODUCT08_ID")
    private String id;

    private String name;
}
