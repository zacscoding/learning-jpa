package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class RemoveEntityDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Member memberA = em.find(Member.class, "memberA");

            // 1) 삭제 쿼리를 쓰기 지연 SQL 저장소에 등록
            // 2) 영속성 컨텍스트에서 제거 (재사용 말고 GC에 관리되도록 하는게 좋음)
            em.remove(memberA);

            tx.commit();
        } finally {
            tx.rollback();
            em.clear();
        }

        emf.close();
    }
}
