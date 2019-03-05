package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 영속성
 *
 * @GitHub : https://github.com/zacscoding
 */
public class ManagedDemoMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // 1) 비영속(new/transient) 상태
            Member member = Member.builder()
                .id("id1")
                .age(19)
                .username("사용자1")
                .build();

            // 2) 영속(managed)로 변경
            // -> a. 1차 캐시에 key(@Id) / value(Object) 로 저장
            // -> b. 쓰기 지연 SQL 저장소에 INSERT SQL 생성
            em.persist(member);

            // 1) 1차 캐시에서 조회
            // 2) 데이터베이스에서 조회
            Member find = em.find(Member.class, member.getId());
            Member find2 = em.find(Member.class, member.getId());

            // Output
            // 영속엔티티 == 조회 엔티티 : true
            // 조회 엔티티1 == 조회 엔티티2 : true
            System.out.println("영속엔티티 == 조회 엔티티 : " + (member == find));
            System.out.println("조회 엔티티1 == 조회 엔티티2 : " + (find == find2));

            // 1) flush() : 쓰기 지연 SQL 저장소 쿼리문 실행
            // 2) commit()
            tx.commit();
        } finally {
            em.close();
        }

        emf.close();
    }
}
