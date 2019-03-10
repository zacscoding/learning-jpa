package jpabook.onetomany.bidirection;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class Team04 {

    @Id
    @GeneratedValue
    @Column(name = "TEAM04_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM04_ID")
    private List<Member04> members = new ArrayList<>();
}
