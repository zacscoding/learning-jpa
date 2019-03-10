package jpabook.transitive;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class TransitivePersistenceDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();
            Child02 child1 = new Child02();
            Child02 child2 = new Child02();

            Parent02 parent = new Parent02();
            child1.setParent(parent);
            child2.setParent(parent);
            parent.getChildren().add(child1);
            parent.getChildren().add(child2);

            em1.persist(parent);
            transaction1.commit();
//            Output ::
//            Hibernate: /* insert jpabook.transitive.Parent02 */ insert into parent02 (id) values (?)
//            Hibernate: /* insert jpabook.transitive.Child02 */ insert into child02 (parent, id) values (?, ?)
//            Hibernate: /* insert jpabook.transitive.Child02 */ insert into child02 (parent, id) values (?, ?)
        } finally {
            if (emf != null) {
                emf.close();
            }
        }

    }
}
