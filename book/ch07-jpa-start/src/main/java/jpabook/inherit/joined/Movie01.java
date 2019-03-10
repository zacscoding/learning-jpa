package jpabook.inherit.joined;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("M")
public class Movie01 extends Item01 {

    private String director;
    private String actor;
}
