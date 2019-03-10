package jpabook.onetooneidentifying;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class BoardDetail09 {

    @Id
    private Long boardId;

    @MapsId // @Id를 사용해 식별자로 지정한 BoardDetail09.boardId 와 매핑
    @OneToOne
    @JoinColumn(name = "BOARD09_ID")
    private Board09 board;

    private String content;
}
