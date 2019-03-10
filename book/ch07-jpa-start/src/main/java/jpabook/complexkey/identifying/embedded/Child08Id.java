package jpabook.complexkey.identifying.embedded;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"parentId", "id"})
@Embeddable
public class Child08Id implements Serializable {

    private String parentId;    // @MapsId("parentId") 매핑

    @Column(name = "CHILD08_ID")
    private String id;
}
