package jpabook;

import java.util.Objects;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpabook.entity.Member;
import jpabook.entity.Team;

/**
 * https://github.com/holyeye/jpabook
 */
public class AbstractDemoRunner {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

    public Team saveTeam(Team team) {
        this.doTask("Save member " + team.toString(), em -> {
            em.persist(team);
        });

        return team;
    }

    public Member saveMember(Member member) {
        this.doTask("Save member " + member.toString(), em -> {
            em.persist(member);
        });

        return member;
    }

    public void doTask(Consumer<EntityManager> entityManagerConsumer) {
        doTask("", entityManagerConsumer);
    }

    public void doTask(String title, Consumer<EntityManager> entityManagerConsumer) {
        Objects.requireNonNull(entityManagerConsumer, "entityManagerConsumer must be not null");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            System.out.println("---------------------------- " + title + "----------------------------");
            transaction.begin();
            entityManagerConsumer.accept(entityManager);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            transaction.rollback();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            System.out.println("---------------------------------------------------------------------------");
        }
    }

    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
