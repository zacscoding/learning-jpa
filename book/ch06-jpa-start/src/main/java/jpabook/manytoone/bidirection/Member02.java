package jpabook.manytoone.bidirection;

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
public class Member02 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER02_ID")
    private Long id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM02_ID")
    public Team02 team;

    public void setTeam(Team02 team) {
        this.team = team;

        // 무한루프에 빠지지 않도록 체크
        if (!team.getMembers().contains(this)) {
            team.getMembers().add(this);
        }
    }
}
