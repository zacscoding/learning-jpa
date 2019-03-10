package jpabook.onetomany.direction;

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
public class Team03 {

    @Id
    @GeneratedValue
    @Column(name = "TEAM03_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM03_ID")
    private List<Member03> members = new ArrayList<>();
}
