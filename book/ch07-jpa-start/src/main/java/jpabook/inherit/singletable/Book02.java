package jpabook.inherit.singletable;

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
@DiscriminatorValue("B")
public class Book02 extends Item02 {

    private String author;
    private String isbn;
}
