package jpabook.complexkey.identifying.embedded;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@EqualsAndHashCode(of = {"id", "child"})
@Entity
public class GrandChild08 {

    @EmbeddedId
    private GrandChild08Id id;

    @MapsId("childId")  // GrandChild08Id.childId 매핑
    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "PARENT08_ID"),
        @JoinColumn(name = "CHILD08_ID")
    })
    private Child08 child;

    private String name;
}
