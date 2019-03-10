package jpabook.inherit.joined;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 조인 전략을 사용한 상속 매핑
@DiscriminatorColumn(name = "DTYPE")    // 저장된 자식 테이블 구분
public class Item01 {

    @Id
    @GeneratedValue
    @Column(name = "ITEM01_ID")
    private Long id;

    private String name;
    private int price;
}
