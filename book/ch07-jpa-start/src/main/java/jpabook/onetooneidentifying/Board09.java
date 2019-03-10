package jpabook.onetooneidentifying;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Board09 {

    @Id
    @GeneratedValue
    @Column(name = "BOARD09_ID")
    private Long id;

    private String title;

    @OneToOne(mappedBy = "board")
    private BoardDetail09 boardDetail;

}
