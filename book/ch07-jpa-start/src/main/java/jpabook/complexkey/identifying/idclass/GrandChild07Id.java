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
@EqualsAndHashCode(of = {"child", "id"})
public class GrandChild07Id implements Serializable {

    private Child07 child; // GrandChild07.child 매핑
    private String id;      // GrandChild07.id 매핑

}
