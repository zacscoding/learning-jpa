package jpabook.multipletable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@Table(name = "BOARD14")
@SecondaryTable(
    name = "BOARD14_DETAIL",    // 매핑할 다른 테이블의 이름
    pkJoinColumns = @PrimaryKeyJoinColumn(name = "BOARD14_DETAIL_ID")   // 매핑할 다른 테이블의 기본 키 컬럼 속성
)
public class Board14 {

    @Id
    @GeneratedValue
    @Column(name = "BOARD14_ID")
    private Long id;

    private String title;

    @Column(table = "BOARD14_DETAIL")
    private String content;
}
