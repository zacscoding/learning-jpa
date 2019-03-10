package jpabook.jpql;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.IntStream;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpabook.AbstractDemoRunner;
import jpabook.entity.Member;

/**
 * https://github.com/holyeye/jpabook
 */
public class JqplDemo {

    public static void main(String[] args) {
        final AbstractDemoRunner runner = new AbstractDemoRunner();

        try {
            IntStream.rangeClosed(1, 5).forEach(i -> {
                runner.saveMember(
                    new Member(null, "유저" + i, i, null, null)
                );
            });

            // defaultApi(runner);
            parameterBinding(runner);

        } finally {
            runner.close();
        }
    }

    private static void parameterBinding(AbstractDemoRunner runner) {
        try {
            runner.doTask("이름 기준 파라미터", em -> {
                String usernameParam = "유저1";
                TypedQuery<Member> query
                    = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);

                query.setParameter("username", usernameParam);

                List<Member> results = query.getResultList();
                for (Member member : results) {
                    System.out.println("Member.username : " + member.getUsername());
                }
            });

            runner.doTask("위치 기준 파라미터", em -> {
                String usernameParam = "유저1";
                List<Member> results = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
                    .setParameter(1, usernameParam)
                    .getResultList();

                for (Member member : results) {
                    System.out.println("Member.username : " + member.getUsername());
                }
            });

//            ---------------------------- 이름 기준 파라미터----------------------------
//            Hibernate: /* SELECT m FROM Member m where m.username = :username */ select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name=?
//            Member.username : 유저1
//            ---------------------------------------------------------------------------
//            ---------------------------- 위치 기준 파라미터----------------------------
//            Hibernate: /* SELECT m FROM Member m where m.username = ?1 */ select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name=?
//            Member.username : 유저1
//            ---------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void defaultApi(AbstractDemoRunner runner) {
        try {
            // TypeQuery 사용
            runner.doTask("TypedQuery 사용", em -> {
                TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
                List<Member> result = query.getResultList();
                for (Member member : result) {
                    System.out.printf("Member id : %d / name : %s\n", member.getId(), member.getUsername());
                }
            });

            runner.doTask("Query 사용", em -> {
                Query query = em.createQuery("SELECT m.username, m.age from Member m");
                List resultList = query.getResultList();
                for (Object o : resultList) {
                    Object[] result = (Object[]) o; // 결과가 둘 이상이면 Object[] 반환
                    System.out.printf("username = %s, age = %s\n", result[0], result[1]);
                }
            });

//            ---------------------------- TypedQuery 사용----------------------------
//            Hibernate: /* SELECT m FROM Member m */ select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_
//            Member id : 1 / name : 유저1
//            Member id : 2 / name : 유저2
//            Member id : 3 / name : 유저3
//            Member id : 4 / name : 유저4
//            Member id : 5 / name : 유저5
//                ---------------------------------------------------------------------------
//                ---------------------------- Query 사용----------------------------
//            Hibernate: /* SELECT m.username, m.age from Member m */ select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
//                username = 유저1, age = 1
//            username = 유저2, age = 2
//            username = 유저3, age = 3
//            username = 유저4, age = 4
//            username = 유저5, age = 5
//                ---------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
