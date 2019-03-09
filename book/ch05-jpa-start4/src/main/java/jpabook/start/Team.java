package jpabook.start;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    private String id;

    private String name;

    // 양방향 관계를 위해 추가 된 필드
    // mappedBy 반대쪽 매핑 필드 이름 값
    // 연관관계의 주인인 Member.team
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
