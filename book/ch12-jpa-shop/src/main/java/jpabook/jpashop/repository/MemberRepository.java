package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Bean 등록 + 스프링의 추상화 예외 전가
 *
 * @GitHub : https://github.com/zacscoding
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
}
