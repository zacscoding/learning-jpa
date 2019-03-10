package jpabook;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        emf.close();
    }
}
