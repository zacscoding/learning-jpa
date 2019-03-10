package jpabook.basicvalue;

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
public class Member01 {

    @Id
    @GeneratedValue
    private Long id;

    // 기본 값 타입
    private String name;
    private int age;
}
