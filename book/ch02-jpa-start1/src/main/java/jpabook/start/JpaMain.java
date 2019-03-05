package jpabook.start;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class JpaMain {

    public static void main(String[] args) {
        // 엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        // 엔티티 매니저 생성
        EntityManager em = emf.createEntityManager();

        // 트랜잭션 획득
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션 시작
            logic(em);  // 비즈니스 롲깃 ㅣㄹ행
            tx.commit(); // 트랜잭션 커밋
        } catch (Exception e) {
            tx.rollback(); // 트랜잭션 롤백
        } finally {
            em.clear(); // 엔티티 매니저 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    private static void logic(EntityManager em) {
        Member member = Member.builder()
            .id("id1")
            .username("zaccoding")
            .age(19)
            .build();

        // 등록
        em.persist(member);

        // 수정
        member.setAge(20);

        // 조회
        Member findMember = em.find(Member.class, member.getId());
        System.out.println("Find member :: " + findMember);

        // 목록 조회 (JPQL)
        List<Member> members = em.createQuery("SELECT m from Member m", Member.class).getResultList();
        System.out.println("members size :: " + members.size());

        // 삭제
        em.remove(member);
    }
}
