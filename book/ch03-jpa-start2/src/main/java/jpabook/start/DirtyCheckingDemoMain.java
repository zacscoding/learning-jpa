package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 변경 감지
 *
 * @GitHub : https://github.com/zacscoding
 */
public class DirtyCheckingDemoMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 영속 엔티티 조회 (1차 캐시 -> Database)
            Member memberA = em.find(Member.class, "memberA");

            // 영속 엔티티 데이터 수정
            memberA.setUsername("newUserId");
            memberA.setAge(10);

            // 1) flush() 실행
            // 2) 1차 캐시에 스냅샷 저장
            // 3) 1차 캐시에서 엔티티와 스냅샷을 비교하여 변경된 엔티티가
            // 존재하면 수정 쿼리를 생성해 쓰기 지연 SQL 저장소에 보냄
            // 4) 쓰기 지연 저장소의 SQL을 데이터베이스 보냄
            // 5) 트랜잭션 커밋

            tx.commit();
        } finally {
            tx.rollback();
            em.clear();
        }

        emf.close();
    }
}
