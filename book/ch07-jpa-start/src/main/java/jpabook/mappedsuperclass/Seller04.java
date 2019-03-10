package jpabook.mappedsuperclass;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Seller04 extends BaseEntity04 {

    // ID 상속
    // NAME 상속
    private String shopName;
}
