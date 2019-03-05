package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class MergeDemoMain {

    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) {
        // 영속성 컨텍스트1에서 영속 상태였다가 영속성 컨텍스트1이 종료되면서
        // 준영속 상태가 됨
        Member member = createMember("memberA", "회원1");

        // 엔티티를 변경해도 준영속 상태이므로 데이터베이스 반영X
        member.setUsername("회원명변경");

        mergeMember(member);
    }

    private static Member createMember(String id, String username) {
        /*  영속성 컨텍스트1 시작*/
        EntityManager em1 = emf.createEntityManager();
        EntityTransaction tx1 = em1.getTransaction();
        tx1.begin();

        Member member = Member.builder()
            .id(id)
            .username(username)
            .age(0)
            .build();

        em1.persist(member);
        tx1.commit();

        // 영속성 컨텍스트1 종료
        // => member는 준영속 상태가 됨
        em1.close();
        /*  영속성 컨텍스트1 종료  */

        return member;
    }

    private static void mergeMember(Member member) {
        /*  영속성 컨텍스트2 시작  */
        EntityManager em2 = emf.createEntityManager();
        EntityTransaction tx2 = em2.getTransaction();

        tx2.begin();
        // 1) merge() 실행
        // 2) 1차 캐시 엔티티 찾기
        // 2-1) DB 조회 후 1차 캐시 (DB에도 없으면 새로운 엔티티 생성)
        // 3) 병합하기(값 채우기) => member의 값을 mergeMember로
        // 4) mergeMember 반환(영속 상태)
        Member mergeMember = em2.merge(member);
        tx2.commit();

        // 준영속 상태
        System.out.println("member = " + member.getUsername());

        // 영속 상태
        System.out.println("mergeMember = " + mergeMember.getUsername());

        System.out.println("member==mergeMember : " + (member == mergeMember));

        System.out.println("em2 contains member = " + em2.contains(member));
        System.out.println("em2 contains mergeMember = " + em2.contains(mergeMember));

//        Output
//        member = 회원명변경
//        mergeMember = 회원명변경
//        member==mergeMember : false
//        em2 contains member = false
//        em2 contains mergeMember = true

        em2.close();
        /*  영속성 컨텍스트2 종료  */
    }
}