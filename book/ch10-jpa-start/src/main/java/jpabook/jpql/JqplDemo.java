package jpabook.jpql;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import jpabook.AbstractDemoRunner;
import jpabook.dto.UserDTO;
import jpabook.entity.Address;
import jpabook.entity.Member;
import jpabook.entity.Team;

/**
 * https://github.com/holyeye/jpabook
 */
public class JqplDemo {

    public static void main(String[] args) {
        final AbstractDemoRunner runner = new AbstractDemoRunner();

        try {
            final Team[] teams = new Team[]{
                runner.saveTeam(new Team(null, "team01", null)),
                runner.saveTeam(new Team(null, "team02", null)),
                runner.saveTeam(new Team(null, "team03", null))
            };

            IntStream.rangeClosed(1, 5).forEach(i -> {
                Team team = teams[new Random().nextInt(teams.length)];
                runner.saveMember(
                    new Member(null, "유저" + i, i, team, null)
                );
            });

            // defaultApi(runner);
            // parameterBinding(runner);
            // projection(runner);
            // pagingAPI(runner);
            // setAndSort(runner);
            // reportingQuery(runner);
            jpqlJoin(runner);
        } finally {
            runner.close();
        }
    }

    private static void jpqlJoin(AbstractDemoRunner runner) {
        try {
            runner.doTask("내부 조인", em -> {
                String teamName = "team01";
                // Team 이라 하지 않고, m.team 으로 조인
                // 외부 조인은 Outer로
                String query = "SELECT m FROM Member m INNER JOIN m.team t "
                    + "WHERE t.name = :teamName";
                List<Member> results = em.createQuery(query, Member.class)
                    .setParameter("teamName", teamName)
                    .getResultList();

                for (Member member : results) {
                    System.out.printf(
                        "Username : %s(%d) in %s\n", member.getUsername(), member.getAge(), member.getTeam().getName()
                    );
                }
            });

            runner.doTask("컬렉션 조인", em -> {
                List<Object[]> teams = em.createQuery("SELECT t, m FROM Team t LEFT JOIN t.members m")
                    .getResultList();

                for (Object[] team : teams) {
                    System.out.println(Arrays.toString(team));
                }
            });

            runner.doTask("세타조인", em -> {
                // SQL : select count(member0_.member_id) as col_0_0_ from member member0_ cross join team team1_ where member0_.name=team1_.name
                Object result = em.createQuery("SELECT COUNT(m) from Member m, Team t where m.username = t.name")
                    .getSingleResult();

                System.out.println("Count :: " + result);
            });

            runner.doTask("JOIN ON", em -> {
                // SQL : select member0_.member_id as member_i1_0_0_, team1_.team_id as team_id1_3_1_, member0_.age as age2_0_0_, member0_.team as team4_0_0_, member0_.name as name3_0_0_, team1_.name as name2_3_1_ from member member0_ left outer join team team1_ on member0_.team=team1_.team_id and (team1_.name='team01')
                List<Object[]> results
                    = em.createQuery("SELECT m,t from Member m left join m.team t on t.name = 'team01'")
                    .getResultList();

                for (Object[] result : results) {
                    Member member = (Member) result[0];
                    Team team = (Team) result[1];
                    System.out.println("Member.username : " + member.getUsername()
                        + "Team.name : " + (team == null ? "null" : team.getName()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void reportingQuery(AbstractDemoRunner runner) {
        try {
            runner.doTask("GROUP BY", em -> {
                // select team1_.name as col_0_0_, count(member0_.age) as col_1_0_, sum(member0_.age) as col_2_0_, max(member0_.age) as col_3_0_, min(member0_.age) as col_4_0_ from member member0_ left outer join team team1_ on member0_.team=team1_.team_id group by team1_.name
                List<Object[]> results =
                    em.createQuery("SELECT t.name, COUNT(m.age), SUM(m.age), MAX(m.age), MIN(m.age)"
                        + " from Member m LEFT JOIN m.team t GROUP BY t.name")
                        .getResultList();

                for (Object[] result : results) {
                    System.out.println(Arrays.toString(result));
                }
//              [team01, 1, 1, 1, 1]
//              [team02, 1, 4, 4, 4]
//              [team03, 1, 5, 5, 5]
            });

            runner.doTask("Order by", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ order by member0_.age DESC, member0_.name ASC
                List<Member> results =
                    em.createQuery("SELECT m from Member m order by m.age DESC, m.username ASC", Member.class)
                        .getResultList();

                for (Member member : results) {
                    System.out.printf("Member username : %s, age : %d\n", member.getUsername(), member.getAge());
                }
//                Member username : 유저4, age : 4
//                Member username : 유저2, age : 2
//                Member username : 유저1, age : 1
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void setAndSort(AbstractDemoRunner runner) {
        try {
            runner.doTask("집합 함수", em -> {
                // select count(member0_.member_id) as col_0_0_, sum(member0_.age) as col_1_0_, avg(cast(member0_.age as double)) as col_2_0_, max(member0_.age) as col_3_0_, min(member0_.age) as col_4_0_ from member member0_
                List<Object[]> aggregate = em
                    .createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) from Member m")
                    .getResultList();

                for (Object[] result : aggregate) {
                    System.out.println(Arrays.toString(result));
                }

//                Output
//                [5, 15, 3.0, 5, 1]
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void pagingAPI(AbstractDemoRunner runner) {
        try {
            runner.doTask("페이징 사용", em -> {
                // Database Dialect에 따른 쿼리 생성
                TypedQuery<Member> query = em.createQuery("SELECT m from Member m ORDER BY m.username DESC"
                    , Member.class);

                query.setFirstResult(2);   // 조회 시작 위치(0부터시작)
                query.setMaxResults(20);    // 조회 할 데이터 수

                System.out.println("Search size : " + query.getResultList().size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void projection(AbstractDemoRunner runner) {
        try {
            runner.doTask("임베디드 타입 프로젝션", em -> {
                // SQL : select order0_.city as col_0_0_, order0_.street as col_0_1_, order0_.zipcode as col_0_2_ from orders order0_
                String query = "SELECT o.address FROM Order o";
                List<Address> addresses = em.createQuery(query, Address.class)
                    .getResultList();
                System.out.println("Address search result size : " + addresses.size());
            });

            runner.doTask("스칼라 타입 프로젝션", em -> {
                // SQL : select distinct member0_.name as col_0_0_ from member member0_
                List<String> usernames = em.createQuery("SELECT DISTINCT username FROM Member m")
                    .getResultList();

                System.out.println("Usernames size : " + usernames.size());
            });

            runner.doTask("여러 프로젝션 Object[] 조회", em -> {
                // SQL : select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                List<Object[]> results = em.createQuery("SELECT m.username, m.age FROM Member m")
                    .getResultList();

                for (Object[] row : results) {
                    String username = (String) row[0];
                    Integer age = (Integer) row[1];
                    System.out.printf("username : %s (%d)\n", username, age);
                }
            });

            runner.doTask("NEW 명령어", em -> {
                // SQL : select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                TypedQuery<UserDTO> query = em.createQuery("SELECT new jpabook.dto.UserDTO(m.username, m.age)"
                    + "FROM Member m", UserDTO.class);
                System.out.println("DTO size :: " + query.getResultList().size());
            });


        } catch (Exception e) {
            e.printStackTrace(System.err);
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
