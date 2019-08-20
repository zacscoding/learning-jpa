package jpabook.performance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.FetchMode;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class PerformanceMember1 {

    @Id
    @GeneratedValue
    private Long id;

    // @org.hibernate.annotations.BatchSize(size = 5)
    @org.hibernate.annotations.Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    //@OneToMany(mappedBy = "member", fetch = FetchType.LAZY) // default
    private List<PerformanceOrder1> orders = new ArrayList<>();
}
