package jpabook.orphan;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpabook.transitive.Child02;
import jpabook.transitive.Parent02;

/**
 * https://github.com/holyeye/jpabook
 */
public class OrphanDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();
            Child03 child1 = new Child03();
            Parent03 parent = new Parent03();
            parent.getChildren().add(child1);
            parent.setId("parent01");
            child1.setParent(parent);

            em1.persist(parent);
            transaction1.commit();
            em1.close();

            EntityManager em2 = emf.createEntityManager();
            EntityTransaction transaction2 = em2.getTransaction();
            transaction2.begin();
            Parent03 find = em2.find(Parent03.class, parent.getId());
            find.getChildren().remove(0);
            transaction2.commit();

//            Hibernate: call next value for hibernate_sequence
//            Hibernate: /* insert jpabook.orphan.Parent03 */ insert into parent03 (parent03_id) values (?)
//            Hibernate: /* insert jpabook.orphan.Child03 */ insert into child03 (parent, id) values (?, ?)
//            Hibernate: select parent03x0_.parent03_id as parent1_4_0_ from parent03 parent03x0_ where parent03x0_.parent03_id=?
//            Hibernate: select children0_.parent as parent2_4_0_, children0_.id as id1_1_0_, children0_.id as id1_1_1_, children0_.parent as parent2_1_1_ from child03 children0_ where children0_.parent=?
//            Hibernate: /* delete jpabook.orphan.Child03 */ delete from child03 where id=?
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

    }
}
