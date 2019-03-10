package jpabook.inherit.joined;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("B")
@PrimaryKeyJoinColumn(name = "BOOK01_ID") // 자식테이블의 기본 키 변경
public class Book01 extends Item01 {

    private String author;
    private String isbn;
}
