package jpabook.start;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * 플러시
 * => 영속성 컨텍스트에 보관된 엔티티를 지우는 것이 아니라 변경 내용을 데이터베이스에 동기화하는 것
 *
 * @GitHub : https://github.com/zacscoding
 */
public class FlushDemoMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Member member = Member.builder()
                .id("id1")
                .age(19)
                .username("사용자1")
                .build();

            em.persist(member);

            // 플러시 호출1) 직접 호출
            // 테스트나 다른 프레임워크와 JPA를 함꼐 사용할 때를 제외하고 거의 사용X
            em.flush();

            // 플러시 호출2) 트랜잭션 커밋
            tx.commit();

            // 플러시 호출3) JPQL 쿼리 실행
            // SQL로 변환되어 데이터베이스에서 직접 조회하므로 영속성 컨텍스트에서 관리되는 엔티티들이
            // 데이터베이스에 저장되어 있어야 조회가 가능해짐
            // beforeQuery() 메소드에서 em.flush()가 호출 됨
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> findAll = query.getResultList();

            // 플러시 모드 옵션
            em.setFlushMode(FlushModeType.AUTO); // 커밋이나 쿼리를 실행할 때 플러시(기본값)
            em.setFlushMode(FlushModeType.COMMIT); // 커밋할 떄만 플러시

        } finally {
            tx.rollback();
            em.clear();
        }

        emf.close();
    }

}
