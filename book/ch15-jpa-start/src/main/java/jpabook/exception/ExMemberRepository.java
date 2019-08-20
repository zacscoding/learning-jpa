package jpabook.exception;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

/**
 * ExMember 기반 repository
 * @GitHub : https://github.com/zacscoding
 */
@Repository
public class ExMemberRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(ExMember member) {
        em.persist(member);
    }

    public ExMember findMemberWithSpringEx() {
        return em.createQuery("select m from ExMember m", ExMember.class)
                 .getSingleResult();
    }

    // or throw NoResultException(JPA ex)
    public ExMember findMemberWithOriginEx() throws Exception {
        return em.createQuery("select m from ExMember m", ExMember.class)
                 .getSingleResult();
    }
}
