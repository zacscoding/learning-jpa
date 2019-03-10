package jpabook.start.keys;

import javax.persistence.Column;
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
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TABLE_KEY_BOARD2")
@TableGenerator(
    name = "BOARD2_SEQ_GENERATOR",
    table = "MY_SEQUENCES",
    pkColumnName = "sequence_name",
    valueColumnName = "sequence_value",
    pkColumnValue = "BOARD2_ID_SEQ",
    initialValue = 0,
    allocationSize = 1
)
public class TableKeyBoard2 {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BOARD2_SEQ_GENERATOR")
    private Long id;

    private String title;
}
