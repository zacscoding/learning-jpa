package jpabook.manytomany.relationentity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@EqualsAndHashCode(of = {"member", "product"})
public class Member08Product08Id implements Serializable {

    private String member;
    private String product;
}
