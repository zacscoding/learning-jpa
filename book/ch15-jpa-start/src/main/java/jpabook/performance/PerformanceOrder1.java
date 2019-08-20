package jpabook.performance;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
public class PerformanceOrder1 {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private PerformanceMember1 member;
}
