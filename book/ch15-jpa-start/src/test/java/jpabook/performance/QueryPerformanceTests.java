package jpabook.performance;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * https://github.com/holyeye/jpabook
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class QueryPerformanceTests {

    @PersistenceContext
    EntityManager em;

    PerformanceMember1 member;
    PerformanceOrder1 order;

    private void persist() {
        persist(1);
    }

    private void persist(int orders) {
        persist(1, orders);
    }

    private void persist(int members, int orders) {
        for (int i = 0; i < members; i++) {
            PerformanceMember1 m = new PerformanceMember1();
            em.persist(m);

            if (i == 0) {
                member = m;
            }

            for (int j = 0; j < orders; j++) {
                PerformanceOrder1 o = new PerformanceOrder1();

                m.getOrders().add(o);
                o.setMember(m);

                if (j == 0) {
                    order = o;
                }

                em.persist(o);
            }
        }

        em.flush();
        em.clear();
    }

    @Test
    public void eagerAndNPlugOne() {
        persist();
        System.out.println(">> em.find(PerformanceMember1.class, member.getId())");
        PerformanceMember1 find = em.find(PerformanceMember1.class, member.getId());

        // Query
//        select
//        performanc0_.member_id as member_i1_1_0_,
//                orders1_.member_id as member_i2_2_1_,
//        orders1_.order_id as order_id1_2_1_,
//                orders1_.order_id as order_id1_2_2_,
//        orders1_.member_id as member_i2_2_2_
//                from
//        performance_member1 performanc0_
//        left outer join
//        performance_order1 orders1_
//        on performanc0_.member_id=orders1_.member_id
//        where
//        performanc0_.member_id=?

        System.out.println(
                ">> em.createQuery(\"select m from PerformanceMember1 m\", PerformanceMember1.class)"
        );
        List<PerformanceMember1> result
                = em.createQuery("select m from PerformanceMember1 m", PerformanceMember1.class)
                    .getResultList();

        System.out.println(">> orders " + result.get(0).getOrders().size());
    }

    @Test
    public void fetchJoin() {
        persist();

        // 일대다 조인을 했으므로 결과가 늘어나서 중복된 결과가 나타날 수있음(DISTINCT 를 사용)
        List<PerformanceMember1> result
                = em.createQuery("select m from PerformanceMember1 m join fetch m.orders",
                                 PerformanceMember1.class)
                    .getResultList();

        System.out.println(">> orders " + result.get(0).getOrders().size());

        // Query
//        select
//        performanc0_.id as id1_1_0_,
//                orders1_.id as id1_2_1_,
//        orders1_.member_id as member_i2_2_1_,
//                orders1_.member_id as member_i2_2_0__,
//        orders1_.id as id1_2_0__
//                from
//        performance_member1 performanc0_
//        inner join
//        performance_order1 orders1_
//        on performanc0_.id=orders1_.member_id
    }

    @Test
    public void batchSize() {
        // un commend @BatchSize in PerformanceMember1 class
        persist(10, 10);

        List<PerformanceMember1> result =
                em.createQuery("select m from PerformanceMember1 m where m.id > 0",
                               PerformanceMember1.class)
                  .getResultList();

        for (int i = 0; i < 10; i++) {
            result.get(0).getOrders().get(i);
            System.out.println(">> find order " + i);
        }
    }

    @Test
    public void fetch() {
        // un commend @Fetch in PerformanceMember1 class
        persist(10, 10);

        List<PerformanceMember1> result =
                em.createQuery("select m from PerformanceMember1 m where m.id > 5",
                               PerformanceMember1.class)
                  .getResultList();

//        select
//        performanc0_.id as id1_1_
//                from
//        performance_member1 performanc0_
//        where
//        performanc0_.id>5
//
//        select
//        orders0_.member_id as member_i2_2_1_,
//                orders0_.id as id1_2_1_,
//        orders0_.id as id1_2_0_,
//                orders0_.member_id as member_i2_2_0_
//        from
//        performance_order1 orders0_
//        where
//        orders0_.member_id in (
//                select
//        performanc0_.id
//                from
//        performance_member1 performanc0_
//        where
//        performanc0_.id>5
//        )



    }
}
