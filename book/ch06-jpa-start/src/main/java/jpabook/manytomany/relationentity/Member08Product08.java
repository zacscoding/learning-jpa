package jpabook.manytomany.relationentity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(Member08Product08Id.class)
public class Member08Product08 {

    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER08_ID")
    private Member08 member; // Member08Product08Id.member와 연결

    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT08_ID")
    private Product08 product; // Member08Product08Id.product와 연결

    private int orderAmount;
}
