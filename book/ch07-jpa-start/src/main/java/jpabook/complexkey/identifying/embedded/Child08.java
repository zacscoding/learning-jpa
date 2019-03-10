package jpabook.complexkey.identifying.embedded;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "parent"})
@Entity
public class Child08 {

    @EmbeddedId
    private Child08Id id;

    // @MapsId 외래키와 매핑한 연관관계를 기본 키에도 매핑
    @MapsId("parentId") // Child08.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT08_ID")
    private Parent08 parent;

    private String name;
}
