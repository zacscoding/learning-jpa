package jpabook.complexkey.nonidentifying.embedded;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * - @EmbeededId 어노테이션 필요
 * - Serialize 구현
 * - equals, hashCode 구현
 * - 기본 생성자
 * - public 클래스
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id1", "id2"})
@Embeddable
public class Parent06Id implements Serializable {

    @Column(name = "PARENT06_ID1")
    private String id1;

    @Column(name = "PARENT06_ID2")
    private String id2;

}
