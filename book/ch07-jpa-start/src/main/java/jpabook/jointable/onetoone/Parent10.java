package jpabook.jointable.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Parent10 {

    @Id
    @GeneratedValue
    @Column(name = "PARENT10_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinTable(
        name = "PARENT10_CHILD10",  // 매핑할 조인 테이블 이름
        joinColumns = @JoinColumn(name = "PARENT10_ID"),    // 현재 엔티티를 참조하는 외래 키
        inverseJoinColumns = @JoinColumn(name = "CHILD10_ID")   // 반대방향 엔티티를 참조하는 외래 키
    )
    private Child10 child;
}
