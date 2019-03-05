package jpabook.start;

import javax.persistence.EntityManager;

/**
 * Detach 준영속 상태
 *
 * @GitHub : https://github.com/zacscoding
 */
public class DetachDemoMain {

    public void testDetached(EntityManager entityManager) {
        Member member = Member.builder()
            .id("member")
            .username("hivava")
            .age(19)
            .build();

        // 영속 상태로 변경
        // 1) 1차 캐시에 저장
        // 2) 쓰기 지연 SQL 저장소에 쿼리 등록
        entityManager.persist(member);

        // 1) 1차 캐시에서 제거
        // 2) 쓰기 지연 SQL 저장소에서 관련 쿼리 제거
        // 영속 컨텍스트가 지원하는 어떤 기능도 동작X
        entityManager.detach(member);
    }

    public void testClear(EntityManager entityManager) {
        // 엔티티 조회, 영속 상태
        Member member = entityManager.find(Member.class, "member");

        // 영속성 컨텍스트 초기화 (1차 캐시 및 쓰기 지연 SQL 저장소 초기화)
        entityManager.close();

        // 준영속 상태의 엔티티
        member.setUsername("newUserName");
    }

    public void testClose(EntityManager entityManager) {
        // logic..

        // 영속성 컨텍스트 종료
        entityManager.close();
    }
}
