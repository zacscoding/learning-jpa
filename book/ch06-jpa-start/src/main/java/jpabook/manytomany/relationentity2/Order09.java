package jpabook.manytomany.relationentity2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Order09 {

    @Id
    @GeneratedValue
    @Column(name = "ORDER09_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER09_ID")
    private Member09 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT09_ID")
    private Product09 product;

    private int orderAmount;
}
