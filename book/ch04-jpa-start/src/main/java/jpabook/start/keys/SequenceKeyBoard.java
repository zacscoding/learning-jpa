package jpabook.start.keys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 시퀀스 전략
 * => Oracle, PostgreSQL, DB2, H2 데이터베이스에서 사용
 * => create sequence [sequenceName] start with [initialValue] increment by [allocationSize]
 * => em.persist() 호출 시 데이터베이스 시퀀스를 사용해서 식별자를 조회 후 영속성 컨텍스트에 저장
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@SequenceGenerator(
    name = "BOARD_SEQ_GENERATOR", // 식별자 생성기 이름
    sequenceName = "BOARD_SEQ", // 데이터베이스에 등록되어 있는 시퀀스 이름
    initialValue = 1, // DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 시작하는 수 지정
    allocationSize = 1 // 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨)
)
@Table(name = "SEQUENCE_KEY_BOARD")
public class SequenceKeyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
    @Column(name = "ID")
    private Long id;
}
