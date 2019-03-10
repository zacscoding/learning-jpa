package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpabook.start.keys.TableKeyBoard;
import jpabook.start.keys.TableKeyBoard2;

/**
 * https://github.com/holyeye/jpabook
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;

        try {
            emf = Persistence.createEntityManagerFactory("jpabook");

            EntityManager entityManager = emf.createEntityManager();
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.persist(TableKeyBoard.builder().title("title111").build());
            entityManager.persist(TableKeyBoard2.builder().title("title111").build());

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
