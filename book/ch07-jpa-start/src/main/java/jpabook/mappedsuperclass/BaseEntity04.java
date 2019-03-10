package jpabook.mappedsuperclass;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@MappedSuperclass // 테이블과 관련 없고 엔티티가 공통으로 사용하는 매핑정보만 정의
public abstract class BaseEntity04 {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
