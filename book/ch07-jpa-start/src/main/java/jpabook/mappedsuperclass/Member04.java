package jpabook.mappedsuperclass;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * https://github.com/holyeye/jpabook
 */
@Entity
@AttributeOverrides(
    @AttributeOverride(name = "id", column = @Column(name = "MEMBER04_ID"))
)
public class Member04 extends BaseEntity04 {

    // ID 오버라이딩
    // NAME 상속
    private String email;
}
