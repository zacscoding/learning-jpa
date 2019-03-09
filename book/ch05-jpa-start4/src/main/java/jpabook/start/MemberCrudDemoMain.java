package jpabook.start;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class MemberCrudDemoMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        DoTask tasks = new DoTask(emf);
        tasks.task(em -> saveMembers(em));
        // tasks.task(em -> queryMemberWithGraph(em));
        // tasks.task(em -> queryLogicJoin(em));
        // tasks.task(em -> updateRelation(em));
        // tasks.task(em -> deleteRelation(em));
        tasks.task(em -> biDirection(em));
        tasks.task(em -> testSaveNonOwner(em));

        emf.close();
    }

    private static void saveMembers(EntityManager em) {
        Team team = Team.builder().id("team1").name("팀1").build();
        em.persist(team);

        Member member1 = Member.builder().id("member1").username("멤버1").team(team).build();
        em.persist(member1);

        Member member2 = Member.builder().id("member2").username("멤버2").team(team).build();
        em.persist(member2);
    }

    private static void queryMemberWithGraph(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        Team team = member1.getTeam();
        System.out.println("팀 이름 = " + team.getName());
    }

    private static void queryLogicJoin(EntityManager em) {
//        select directmemb0_.member_id as member_i1_0_, directmemb0_.name as name2_0_, directmemb0_.team_id as team_id3_0_ from direct_member directmemb0_ inner join direct_team directteam1_ on directmemb0_.team_id=directteam1_.team_id where directteam1_.name=?
//        Hibernate: select directteam0_.team_id as team_id1_1_0_, directteam0_.name as name2_1_0_ from direct_team directteam0_ where directteam0_.team_id=?
//        [query] member.username = 멤버1
//        [query] member.username = 멤버2

        String jqpl = "select m from Member m join m.team t where t.name=:teamName";

        List<Member> members = em.createQuery(jqpl, Member.class)
            .setParameter("teamName", "팀1")
            .getResultList();

        for (Member member : members) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    private static void updateRelation(EntityManager em) {
//        Hibernate: /* insert jpabook.start.direction.DirectTeam */ insert into direct_team (name, team_id) values (?, ?)
//        Hibernate: /* update jpabook.start.direction.DirectMember */ update direct_member set name=?, team_id=? where member_id=?

        Team team2 = Team.builder()
            .id("team2")
            .name("팀2")
            .build();

        em.persist(team2);
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em) {

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null);
    }

    private static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();

        for (Member member : members) {
            System.out.println("member.username : " + member.getUsername());
        }
    }

    private static void testSaveNonOwner(EntityManager em) {
        Member member1 = Member.builder()
            .id("new_member1")
            .username("새로운멤버1")
            .build();
        em.persist(member1);

        Member member2 = Member.builder()
            .id("new_member2")
            .username("새로운멤버2")
            .build();
        em.persist(member2);

        Team team1 = Team.builder()
            .id("new_team1")
            .name("새로운팀1")
            .members(Arrays.asList(member1, member2))
            .build();

        em.persist(team1);
    }

    private static class DoTask {

        EntityManagerFactory emf;

        public DoTask(EntityManagerFactory emf) {
            this.emf = emf;
        }

        public void task(Consumer<EntityManager> entityManagerConsumer) {
            EntityManager em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();

            try {
                transaction.begin();
                entityManagerConsumer.accept(em);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                e.printStackTrace(System.err);
            } finally {
                em.close();
            }
        }
    }

}