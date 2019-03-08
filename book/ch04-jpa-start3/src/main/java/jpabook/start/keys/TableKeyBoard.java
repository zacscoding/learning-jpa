package jpabook.start.keys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * => 테이블 하나 생성하고 이름과 값으로 컬럼을 만들어 사용
 * => 모든 데이터베이스에서 사용가능
 *
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TABLE_KEY_BOARD")
@TableGenerator(
    name = "BOARD_SEQ_GENERATOR",    // 식별자 생성기 이름
    table = "MY_SEQUENCES",          // 키 생성 테이블명
    pkColumnName = "sequence_name",  // 시퀀스 컬럼명
    valueColumnName = "sequence_value",// 시퀀스 값 컬럼명
    pkColumnValue = "BOARD_ID_SEQ",  // 키로 사용할 값 이름
    initialValue = 0,                // 초기 값, 마지막으로 생성된 값이 기준
    allocationSize = 1               // 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용 됨)
)
public class TableKeyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD_SEQ_GENERATOR")
    private Long id;

    private String title;
}
