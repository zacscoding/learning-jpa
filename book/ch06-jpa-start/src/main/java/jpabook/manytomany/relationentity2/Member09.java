package jpabook.manytomany.relationentity2;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Member09 {

    @Id
    @Column(name = "MEMBER09_ID")
    private String id;

    private String username;

    @OneToMany(mappedBy = "member")
    private List<Order09> orders = new ArrayList<>();
}
