package jpabook.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Member05 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER05_ID")
    private Long id;

    private String username;

    // 일대일 관계에서 주 테이블(Member05)에 외래 키(locker01_id)를 사용
    @OneToOne
    @JoinColumn(name = "LOCKER05_ID")
    private Locker05 locker;
}
