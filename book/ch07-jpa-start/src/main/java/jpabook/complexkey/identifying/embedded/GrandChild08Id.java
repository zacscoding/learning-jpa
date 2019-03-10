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
@EqualsAndHashCode(of = {"childId", "id"})
@Embeddable
public class GrandChild08Id implements Serializable {

    private Child08Id childId; // @MapsId("childId") 로 매핑

    @Column(name = "GRANDCHILD08_ID")
    private String id;
}
