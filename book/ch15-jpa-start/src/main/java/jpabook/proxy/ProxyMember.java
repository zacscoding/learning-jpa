package jpabook.proxy;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "ProxyMember")
public class ProxyMember {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    @Column(name = "NAME")
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        // proxy의 타입 비교는 == 비교 대신 instanceof 를 사용해야 함
        if (!(o instanceof ProxyMember)) {
            return false;
        }

        // proxy의 멤버필드 접근 대신 접근자 메소드 사용
        final ProxyMember that = (ProxyMember) o;
        return getId().equals(that.getId());
    }
}
