package jpabook.manytoone.direction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Member01 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER01_ID")
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM01_ID")
    public Team01 team;
}
