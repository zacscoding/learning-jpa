package jpabook.manytomany.relationentity;

import com.sun.javafx.beans.IDProperty;
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
public class Member08 {

    @Id
    @Column(name = "MEMBER08_ID")
    private String id;

    private String username;

    @OneToMany(mappedBy = "member")
    private List<Member08Product08> memberProducts = new ArrayList<>();
}
