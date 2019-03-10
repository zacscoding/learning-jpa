package jpabook.proxy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    @Column(name = "MEMBER01_ID")
    private Long id;

    private String username;

    public Member01() {
        System.out.println("Member01() is called..");
    }
}
