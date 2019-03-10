package jpabook.onetoone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Locker05 {

    @Id
    @GeneratedValue
    @Column(name = "LOCKER05_ID")
    private Long id;

    private String name;

    // 양방향 설정
    @OneToOne(mappedBy = "locker")
    private Member05 member;
}
