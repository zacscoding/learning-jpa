package jpabook.manytoone.bidirection;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Team02 {

    @Id
    @GeneratedValue
    @Column(name = "TEAM02_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member02> members = new ArrayList<>();

    public void addMember(Member02 member) {
        this.members.add(member);

        // 무한로프에 빠지지 않도록 체크
        if (member.getTeam() != this) {
            member.setTeam(this);
        }
    }
}
