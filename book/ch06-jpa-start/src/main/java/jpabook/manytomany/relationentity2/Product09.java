package jpabook.manytomany.relationentity2;

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
public class Product09 {

    @Id
    @Column(name = "PRODUCT09_ID")
    private String id;

    private String name;
}