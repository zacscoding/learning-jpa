package jpabook.complexkey.nonidentifying.idclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class NonIdentifyingDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();

            Parent05 parent = new Parent05();
            parent.setId1("myid1");
            parent.setId2("myid2");
            parent.setName("parentName");
            em1.persist(parent);
            transaction1.commit();
            em1.close();

            EntityManager em2 = emf.createEntityManager();
            Parent05Id id = new Parent05Id(parent.getId1(), parent.getId2());
            Parent05 find = em2.find(Parent05.class, id);
            System.out.println("Found : " + find.toString());

//            Output
//            Hibernate: /* insert jpabook.complexkey.nonidentifying.Parent05 */ insert into parent05 (name, parent05_id1, parent05_id2) values (?, ?, ?)
//            Hibernate: select parent05x0_.parent05_id1 as parent1_11_0_, parent05x0_.parent05_id2 as parent2_11_0_, parent05x0_.name as name3_11_0_ from parent05 parent05x0_ where parent05x0_.parent05_id1=? and parent05x0_.parent05_id2=?
//            Found : Parent05(id1=myid1, id2=myid2, name=parentName)
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
