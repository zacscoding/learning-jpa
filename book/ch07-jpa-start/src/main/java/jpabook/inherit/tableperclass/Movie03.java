package jpabook.inherit.tableperclass;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Movie03 extends Item03 {

    private String director;
    private String actor;
}
