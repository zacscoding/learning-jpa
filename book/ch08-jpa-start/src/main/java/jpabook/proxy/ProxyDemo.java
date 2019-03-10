package jpabook.proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class ProxyDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();
            Member01 member = new Member01();
            member.setId(1L);
            member.setUsername("유저01");
            em1.persist(member);
            transaction1.commit();
            em1.close();
            System.out.println("--- save ---");
            EntityManager em2 = emf.createEntityManager();
            Member01 memberProxy = em2.getReference(Member01.class, 1L);
            boolean isLoaded = em2.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(memberProxy);
            System.out.println("Success to find... :: " + memberProxy.getClass().getName());
            System.out.println("isLoladed : " + isLoaded);
            // 1) getUsername() 사용
            // 2) 영속성 컨텍스트에게 초기화 요청
            // 3) DB 조회
            // 4) 실제 Member entity 생성
            // 5) proxy.target.getUsername() 반환
            System.out.println("memberProxy.getUsername() : " + memberProxy.getUsername());

//            Member01() is called..
//            Hibernate: /* insert jpabook.proxy.Member */ insert into member (username, member_id) values (?, ?)
//            --- save ---
//            Member01() is called..
//            Success to find... :: jpabook.proxy.Member_$$_jvstf32_0
//            isLoladed : false
//            Hibernate: select member0_.member_id as member_i1_0_0_, member0_.username as username2_0_0_ from member member0_ where member0_.member_id=?
//            Member01() is called..
//            memberProxy.getUsername() : 유저01
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
