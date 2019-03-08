package jpabook.start;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 추가 된 요구 사항
 * - 회원은 일반 회원과 관리자로 구분되어야 한다.
 * - 회원 가입일과 수정일이 있어야 한다.
 * - 회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "MEMBER", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"NAME", "AGE"}) // add constraint UK_7grc9xe7fcp7fwi54wsxhufk4  unique (name, age)
})
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME", nullable = false, length = 10) // name varchar(10) not null
    private String username;

    private Integer age;

    // 추가
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;
}
