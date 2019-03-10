package jpabook.complexkey.identifying.idclass;

import java.io.Serializable;
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
@EqualsAndHashCode(of = {"parent", "childId"})
public class Child07Id implements Serializable {

    private String parent;  // Child07.parent 매핑
    private String childId; // Child07.childId 매핑
}
