package jpabook.complexkey.nonidentifying.embedded;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * https://github.com/holyeye/jpabook
 */
@Entity
public class Parent06 {

    @EmbeddedId
    private Parent06Id id;

    private String name;
}
