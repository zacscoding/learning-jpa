package jpabook.start;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void setTeam(Team team) {
        // 기존 연관관계 제거
        if (this.team != null) {
            this.team.getMembers().remove(this);
        }

        this.team = team;
        team.getMembers().add(this);
    }
}
