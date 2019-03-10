package jpabook.complexkey.nonidentifying.idclass;

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
@EqualsAndHashCode(of = {"id1", "id2"})
public class Parent05Id implements Serializable {

    private String id1; // Parent05.id1 매핑
    private String id2; // Parent05.id2 매핑
}
