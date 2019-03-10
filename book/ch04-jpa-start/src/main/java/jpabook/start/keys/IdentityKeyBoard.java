package jpabook.start.keys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 데이터베이스에 위임
 * => 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용
 * => em.persist() 호출하면 INSERT SQL이 데이터베이스에 전달되어 트랜잭션을 지원하는 쓰기 지연이 동작X
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@Table(name = "IDENTITY_KEY_BOARD")
public class IdentityKeyBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;
}
