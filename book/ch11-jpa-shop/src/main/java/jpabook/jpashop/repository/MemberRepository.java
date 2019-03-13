package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

/**
 * Bean 등록 + 스프링의 추상화 예외 전가
 *
 * @GitHub : https://github.com/zacscoding
 */
@Repository
public class MemberRepository {

    // @PersistenceUnit
    // private EntityManagerFactory emf;
    @PersistenceContext
    private EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("SELECT m FROM Member m", Member.class)
            .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("SELECT m FROM Member m where m.name = :name", Member.class)
            .setParameter("name", name)
            .getResultList();
    }
}
