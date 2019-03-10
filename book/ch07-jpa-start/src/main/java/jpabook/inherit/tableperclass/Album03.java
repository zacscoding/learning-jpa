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
public class Album03 extends Item03 {

    private String artist;
}
