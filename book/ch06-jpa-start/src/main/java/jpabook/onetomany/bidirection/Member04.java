package jpabook.onetomany.bidirection;

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
public class Member04 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER04_ID")
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM04_ID", insertable = false, updatable = false)
    private Team04 team;
}
